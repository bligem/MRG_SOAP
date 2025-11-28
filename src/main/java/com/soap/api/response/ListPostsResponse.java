package com.soap.api.response;

import com.soap.api.dto.PostDto;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ListPostsResponse")
@XmlType(name = "ListPostsResponseType", namespace = "http://soap.api.com/")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListPostsResponse {

    @XmlElement(name = "post")
    private List<PostDto> posts;

}
