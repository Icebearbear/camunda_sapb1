package io.camunda.demo.process_payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
public class B1SLController {

    private final RestTemplate restTemplate;

    @Autowired
    public B1SLController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Hello World";
    }

    @GetMapping(value= "/callb1")
    private String getB1(){
        String uri = "http://localhost:8080/hello";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        return result;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {

        String apiUrl = "https://DESKTOP-JJJOBP8:50000/b1s/v1/Login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = "{\"CompanyDB\":\"SBODemoSG\", \"UserName\":\"manager\", \"Password\":\"Josg@1234\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                apiUrl, HttpMethod.POST, entity, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

    }
}
