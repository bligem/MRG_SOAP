package com.soap.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@XmlRootElement(name = "externalPostResponse")
//@JacksonXmlRootElement(localName = "externalPostResponse")
@XmlAccessorType(XmlAccessType.FIELD)
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