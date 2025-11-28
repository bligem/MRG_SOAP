package com.soap.api.request.post;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@XmlRootElement(name = "UpdatePostRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdatePostRequest {

    @NotBlank
    private UUID id;

    @Size(min = 3, max = 70)
    private String title;

    @Size(min = 3, max = 5000)
    private String content;

    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "tag")
    private List<String> tags;

    private Boolean published;
}
