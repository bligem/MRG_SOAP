package com.soap.api.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@XmlRootElement(name = "UserRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserRequest {

    @NotBlank
    private UUID id;
}
