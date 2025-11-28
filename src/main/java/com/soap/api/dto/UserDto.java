package com.soap.api.dto;

import com.soap.api.db.User;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    public UUID id;
    public String email;
    public String name;

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    public List<Role> roles;

    public OffsetDateTime createdAt;
    public OffsetDateTime updatedAt;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
