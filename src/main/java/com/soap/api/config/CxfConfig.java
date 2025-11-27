//package com.soap.api.config;
//
//
//import org.apache.cxf.Bus;
//import org.apache.cxf.endpoint.Endpoint;
//import org.apache.cxf.jaxws.EndpointImpl;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//@Configuration
//public class CxfConfig {
//
//    @Bean
//    public Endpoint apiEndpoint(Bus bus, ApiServiceImpl apiService) {
//        EndpointImpl endpoint = new EndpointImpl(bus, apiService);
//        endpoint.publish("/api/external/posts");
//        return endpoint;
//    }
//}