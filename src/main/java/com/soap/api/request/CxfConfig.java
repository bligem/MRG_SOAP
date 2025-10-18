//package com.soap.api.request;
//
//import jakarta.websocket.Endpoint;
//import org.apache.cxf.Bus;
//import org.apache.cxf.endpoint.EndpointImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CxfConfig {
//
//    @Bean
//    public Endpoint userEndpoint(Bus bus, UserSoapServiceImpl impl) {
//        EndpointImpl endpoint = new EndpointImpl(bus, impl);
//        endpoint.publish("/UserService");
//        return endpoint;
//    }
//}