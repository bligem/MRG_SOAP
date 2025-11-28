package com.soap.api.request.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "CreatePostRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class CreatePostRequest {
    @NotBlank
    @Size(min = 3, max = 70)
    private String title;

    @NotBlank
    @Size(min = 3, max = 5000)
    private String content;

    @NotEmpty
    @Size(min = 3)
    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "tag")
    private List<@NotBlank String> tags;

    @NotNull
    private Boolean published;
}
