package com.soap.api.db;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findByPublishedTrueOrderByCreatedAtDesc(Pageable pageable);

    List<Post> findByPublishedTrueOrUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    List<Post> findByUserIdOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    List<Post> findByUserIdAndPublishedTrueOrderByCreatedAtDesc(UUID userId, Pageable pageable);

    long deleteByIdAndUserId(UUID id, UUID userId);
}
