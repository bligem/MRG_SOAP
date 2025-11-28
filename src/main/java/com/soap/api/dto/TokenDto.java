package com.soap.api.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "Token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private UUID userId;
    private List<Role> roles;
}