package com.soap.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.util.List;
import java.util.UUID;

@JacksonXmlRootElement(localName = "token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private UUID userId;
    private List<Role> roles;
}