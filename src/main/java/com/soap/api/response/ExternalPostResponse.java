//package com.soap.api.response;
//
//
//import com.soap.api.dto.ExternalPostDto;
//import jakarta.xml.bind.annotation.XmlAccessType;
//import jakarta.xml.bind.annotation.XmlAccessorType;
//import jakarta.xml.bind.annotation.XmlElement;
//import jakarta.xml.bind.annotation.XmlElementWrapper;
//import jakarta.xml.bind.annotation.XmlRootElement;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//
//import java.util.List;
//
//@Data
//@XmlRootElement(name = "externalPostResponse")
//@XmlAccessorType(XmlAccessType.FIELD)
//public class ExternalPostResponse {
//
//
//    @XmlElementWrapper(name = "posts")
//    @XmlElement(name = "post")
//    private List<ExternalPostDto> posts;
//
//
//    public List<ExternalPostDto> getPosts() { return posts; }
//    public void setPosts(List<ExternalPostDto> posts) { this.posts = posts; }
//}