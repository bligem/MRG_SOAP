package com.soap.api.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "DeleteUserResponse")
@XmlType(name = "DeleteUserResponseType", namespace = "http://soap.api.com/")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DeleteUserResponse {
    private boolean deleted;
    private UUID userId;
}