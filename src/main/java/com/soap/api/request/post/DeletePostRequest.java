package com.soap.api.request.post;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "deletePostRequest")
public class DeletePostRequest {
    @NotBlank
    private UUID id;
}
