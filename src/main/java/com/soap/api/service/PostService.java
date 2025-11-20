package com.soap.api.service;

import com.soap.api.db.PostRepository;
import com.soap.api.db.Post;
import com.soap.api.dto.PostDto;
import com.soap.api.dto.Role;
import com.soap.api.request.post.*;
import com.soap.api.util.RoleUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    private int parseLimit(Integer limit) {
        int l = 10;
        if (limit != null && limit >= 1 && limit <= 50) l = limit;
        return l;
    }

    public PostDto create(CreatePostRequest req, UUID callerId) {
        if (callerId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token");
        }

        String title = req.getTitle() != null ? req.getTitle().trim() : null;
        String content = req.getContent() != null ? req.getContent().trim() : null;

        if (title == null || title.isEmpty() || content == null || content.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and content are required");
        }

        Post p = new Post();
        p.setUserId(callerId);
        p.setTitle(title);
        p.setContent(content);
        p.setTags(req.getTags());
        p.setPublished(Boolean.TRUE.equals(req.getPublished()));
        p.setCreatedAt(OffsetDateTime.now());
        p.setUpdatedAt(OffsetDateTime.now());

        p = repo.save(p);
        return PostDto.fromEntity(p);
    }

    /**
     * List posts (or get single if id provided)
     */
    public List<PostDto> listPosts(ListPostsRequest req, UUID callerId, List<Role> callerRoles) {
        // if id present -> single post behaviour
        if (req != null && req.getId() != null && !req.getId().isBlank()) {
            UUID id = UUID.fromString(req.getId());
            Post post = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

            boolean isAdmin = RoleUtils.hasRole(callerRoles, Role.ADMIN);
            if (!post.isPublished() && (callerId == null || !callerId.equals(post.getUserId())) && !isAdmin) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
            }
            return List.of(PostDto.fromEntity(post));
        }

        int limit = parseLimit(req != null ? req.getLimit() : null);
        var pageable = PageRequest.of(0, limit);

        if (callerId == null) {
            return repo.findByPublishedTrueOrderByCreatedAtDesc(pageable).stream()
                    .map(PostDto::fromEntity).collect(Collectors.toList());
        } else {
            boolean isAdmin = RoleUtils.hasRole(callerRoles, Role.ADMIN);
            if (isAdmin) {
                return repo.findAllByOrderByCreatedAtDesc(pageable).stream().map(PostDto::fromEntity).collect(Collectors.toList());
            } else {
                return repo.findByPublishedTrueOrUserIdOrderByCreatedAtDesc(callerId, pageable).stream()
                        .map(PostDto::fromEntity).collect(Collectors.toList());
            }
        }
    }

    /**
     * List posts by user id (if provided).
     * If caller requests other user's posts and caller is not admin -> only published posts for that user.
     * If caller requests own posts -> return all (including unpublished).
     */
    public List<PostDto> listPostsByUser(ListPostsByUserRequest req, UUID callerId, List<Role> callerRoles) {
        int limit = parseLimit(req != null ? req.getLimit() : null);
        var pageable = PageRequest.of(0, limit);

        UUID requested = null;
        if (req != null && req.getUserId() != null && !req.getUserId().isBlank()) {
            requested = UUID.fromString(req.getUserId());
        }

        boolean isAdmin = RoleUtils.hasRole(callerRoles, Role.ADMIN);

        if (requested != null) {
            if (isAdmin) {
                return repo.findByUserIdOrderByCreatedAtDesc(requested, pageable).stream().map(PostDto::fromEntity).collect(Collectors.toList());
            } else {
                // non-admin: if requesting own posts, allow all; else only published
                if (callerId != null && callerId.equals(requested)) {
                    return repo.findByUserIdOrderByCreatedAtDesc(requested, pageable).stream().map(PostDto::fromEntity).collect(Collectors.toList());
                } else {
                    return repo.findByUserIdAndPublishedTrueOrderByCreatedAtDesc(requested, pageable).stream().map(PostDto::fromEntity).collect(Collectors.toList());
                }
            }
        }

        // no userId provided -> must require callerId (for current user's posts) or return 400
        if (callerId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing user id or token");
        }
        return repo.findByUserIdOrderByCreatedAtDesc(callerId, pageable).stream().map(PostDto::fromEntity).collect(Collectors.toList());
    }

    public PostDto update(UpdatePostRequest req, UUID callerId, List<Role> callerRoles) {
        if (req.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is required");
        }
        UUID id = req.getId();
        Post existing = repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        boolean isAdmin = RoleUtils.hasRole(callerRoles, Role.ADMIN);
        if (!isAdmin && (callerId == null || !callerId.equals(existing.getUserId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        if (req.getTitle() != null) existing.setTitle(req.getTitle().trim());
        if (req.getContent() != null) existing.setContent(req.getContent().trim());
        if (req.getTags() != null) existing.setTags(req.getTags());
        if (req.getPublished() != null) existing.setPublished(req.getPublished());

        existing.setUpdatedAt(OffsetDateTime.now());
        existing = repo.save(existing);
        return PostDto.fromEntity(existing);
    }

    public void delete(DeletePostRequest req, UUID callerId, List<Role> callerRoles) {
        if (req.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is required");
        }
        UUID id = req.getId();

        boolean isAdmin = RoleUtils.hasRole(callerRoles, Role.ADMIN);

        if (isAdmin) {
            if (!repo.existsById(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
            }
            repo.deleteById(id);
            return;
        }

        // not admin -> delete only if owned by caller
        if (callerId == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing token");
        long deleted = repo.deleteByIdAndUserId(id, callerId);
        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found or not owned by you");
        }
    }
}
