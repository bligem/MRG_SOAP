package com.soap.api.request.post;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "API")
public class UpdatePostRequest {

    @NotBlank
    private UUID id;

    @Size(min = 3, max = 70)
    private String title;

    @Size(min = 3, max = 5000)
    private String content;

    @JacksonXmlElementWrapper(localName = "tags")
    @JacksonXmlProperty(localName = "tag")
    private List<String> tags;

    private Boolean published;
}
