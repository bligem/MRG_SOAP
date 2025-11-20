package com.soap.api.request.post;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JacksonXmlRootElement(localName = "API")
public class ListPostsByUserRequest {
    private String userId;
    private Integer limit;
}
