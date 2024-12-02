package io.camunda.demo.process_payments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.camunda.demo.process_payments.ProcessPaymentsApplication;
import io.camunda.demo.process_payments.dto.response.LoginResponse;
import io.camunda.demo.process_payments.service.B1SLControllerService;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.http.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;


@RestController
public class B1SLController {

    private final RestTemplate restTemplate;

    private B1SLControllerService b1SLControllerService;

    @Autowired
    public B1SLController(RestTemplate restTemplate, B1SLControllerService b1SLControllerService){
        this.restTemplate = restTemplate;
        this.b1SLControllerService = b1SLControllerService;
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
    public ResponseEntity<Map> login() {

        String apiUrl = "https://DESKTOP-JJJOBP8:50000/b1s/v1/Login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = "{\"CompanyDB\":\"SBODemoSG\", \"UserName\":\"manager\", \"Password\":\"Josg@1234\"}";
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                apiUrl, HttpMethod.POST, entity, Map.class);


        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            LoginResponse loginResponse = new LoginResponse(response.getBody());

            String apiUrl2 = "https://DESKTOP-JJJOBP8:50000/b1s/v1/BusinessPartners";
            HttpHeaders headers2 = new HttpHeaders();
            headers2.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Cookie", "B1SESSION=" + loginResponse.getSessionId() + "; ROUTEID");

            HttpEntity<String> entity2 = new HttpEntity<>(null, headers2);

            ResponseEntity<String> response2 = restTemplate.exchange(
                    apiUrl2, HttpMethod.GET, null, String.class);

            System.out.println(response2.getStatusCode());
            System.out.println(response2.getBody());
        }


        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

    }

    @GetMapping("/start_process")
    public ResponseEntity<String> startProgress() {
        String instanceKey = Long.toString(this.b1SLControllerService.startService());
        return ResponseEntity.status(HttpStatus.OK).body("Started Instance " + instanceKey);

    }
}



