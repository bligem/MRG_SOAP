package com.soap.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "ExternalPostRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExternalPostRequest {

    @NotBlank
    private Integer limit;
}