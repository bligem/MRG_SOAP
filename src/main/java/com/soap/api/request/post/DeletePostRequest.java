package com.soap.api.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@XmlRootElement(name = "DeletePostRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeletePostRequest {
    @NotBlank
    private UUID id;
}
