package com.soap.api.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.soap.api.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@JacksonXmlRootElement(localName = "loginResponse")
@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserDto user;
}
