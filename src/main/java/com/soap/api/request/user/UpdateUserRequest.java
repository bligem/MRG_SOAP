package com.soap.api.request.user;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@XmlRootElement(name = "UpdateUserRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class UpdateUserRequest {
    // optional: if absent, update current (caller) user
    private UUID id;

    @Email
    private String email;

    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 8)
    private String password;

    @XmlElementWrapper(name = "roles")
    @XmlElement(name = "role")
    private List<String> roles;
}
