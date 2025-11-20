package com.soap.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

@JacksonXmlRootElement(localName = "externalPostResponse")
@Builder
@Data
public class ExternalPostDto {
    public Integer userId;
    public Integer id;
    public String title;
    public String body;
}
