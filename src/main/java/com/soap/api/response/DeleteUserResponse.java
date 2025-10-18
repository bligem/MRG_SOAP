package com.soap.api.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@JacksonXmlRootElement(localName = "deleteUserResponse")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserResponse {
    private boolean deleted;
    private UUID userId;
}