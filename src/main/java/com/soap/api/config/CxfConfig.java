package com.soap.api.config;


import com.soap.api.controller.*;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {

    @Bean
    public EndpointImpl postEndpoint(Bus bus, PostController postEndpoint) {
        EndpointImpl ep = new EndpointImpl(bus, postEndpoint);
        ep.publish("/api/posts");
        return ep;
    }

    @Bean
    public EndpointImpl postEndpointByUser(Bus bus, PostByUserController postEndpoint) {
        EndpointImpl ep = new EndpointImpl(bus, postEndpoint);
        ep.publish("/api/posts/byUser");
        return ep;
    }

    @Bean
    public EndpointImpl userEndpoint(Bus bus, UserController userEndpoint) {
        EndpointImpl ep = new EndpointImpl(bus, userEndpoint);
        ep.publish("/api/user");
        return ep;
    }
    @Bean
    public EndpointImpl usersEndpoint(Bus bus, UsersController userEndpoint) {
        EndpointImpl ep = new EndpointImpl(bus, userEndpoint);
        ep.publish("/api/users");
        return ep;
    }

    @Bean
    public EndpointImpl registerEndpoint(Bus bus, RegisterController authEndpoint) {
        EndpointImpl ep = new EndpointImpl(bus, authEndpoint);
        ep.publish("/api/user/register");
        return ep;
    }

    @Bean
    public EndpointImpl loginEndpoint(Bus bus, LoginController authEndpoint) {
        EndpointImpl ep = new EndpointImpl(bus, authEndpoint);
        ep.publish("/api/user/login");
        return ep;
    }

    @Bean
    public EndpointImpl externalEndpoint(Bus bus, ExternalPostController externalEndpoint) {
        EndpointImpl ep = new EndpointImpl(bus, externalEndpoint);
        ep.publish("/api/external/posts");
        return ep;
    }
}
