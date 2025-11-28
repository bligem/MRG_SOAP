package com.soap.api.request.post;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "ListPostsByUserRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListPostsByUserRequest {
    private String userId;
    private Integer limit;
}
