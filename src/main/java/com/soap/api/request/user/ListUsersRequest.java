package com.soap.api.request.user;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "ListUsersRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListUsersRequest {

    private Integer limit;
}
