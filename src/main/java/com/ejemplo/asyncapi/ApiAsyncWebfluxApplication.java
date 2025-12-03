package com.ejemplo.asyncapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiAsyncWebfluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiAsyncWebfluxApplication.class, args);
        System.out.println("API Asíncrona WebFlux iniciada en http://localhost:8632");
    }
}