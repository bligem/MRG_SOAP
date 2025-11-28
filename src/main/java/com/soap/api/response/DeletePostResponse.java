package com.soap.api.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@XmlRootElement(name = "DeletePostResponse")
@XmlType(name = "DeletePostResponseType", namespace = "http://soap.api.com/")
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class DeletePostResponse {
    private boolean deleted;
    private UUID postId;
}