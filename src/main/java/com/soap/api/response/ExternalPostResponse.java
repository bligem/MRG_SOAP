//package com.soap.api.response;
//
//
//import com.soap.api.dto.ExternalPostDto;
//import jakarta.xml.bind.annotation.*;
//import lombok.Data;
//
//
//import java.util.List;
//
//@Data
//@XmlRootElement(name = "ExternalPostResponse")
//@XmlType(name = "ExternalPostResponseType", namespace = "http://soap.api.com/")
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