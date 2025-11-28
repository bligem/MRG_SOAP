package com.soap.api.request.post;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "ListPostsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListPostsRequest {
    private Integer limit;
    private String id;
}
