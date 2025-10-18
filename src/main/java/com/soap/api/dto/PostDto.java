package com.soap.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.soap.api.db.Post;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JacksonXmlRootElement(localName = "postResponse")
@Builder
@Data
public class PostDto {
    public UUID id;
    public UUID userId;
    public String title;
    public String content;
    @JacksonXmlElementWrapper(localName = "tags")
    @JacksonXmlProperty(localName = "tag")
    public List<String> tags;
    public Boolean published;
    public OffsetDateTime createdAt;
    public OffsetDateTime updatedAt;

    public static PostDto fromEntity(Post p) {
        return PostDto.builder()
                .id(p.getId())
                .userId(p.getUserId())
                .title(p.getTitle())
                .content(p.getContent())
                .tags(p.getTags())
                .published(p.isPublished())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
