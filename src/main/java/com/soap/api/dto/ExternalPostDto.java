package com.soap.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NoArgsConstructor;

@Data
@Builder
@XmlRootElement(name = "ExternalPostResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@AllArgsConstructor
public class ExternalPostDto {


    @XmlElement
    public Integer userId;


    @XmlElement
    public Integer id;


    @XmlElement
    public String title;


    @XmlElement
    public String body;

}