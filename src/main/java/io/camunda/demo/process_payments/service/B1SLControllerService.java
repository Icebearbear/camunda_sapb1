package io.camunda.demo.process_payments.service;

import java.util.Map;

import io.camunda.demo.process_payments.controller.B1SLController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;

@Service
public class B1SLControllerService {
    @Autowired
    private ZeebeClient zeebeClient;
    private static final Logger LOG = LoggerFactory.getLogger(B1SLControllerService.class);

    public long startService(){
        var bpmnProcessId = "process-payments22"; // or whatever the key is
        var event = zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(bpmnProcessId)
                .latestVersion()
                .variables(Map.of("total", 200))
                .send()
                .join();
        LOG.info(String.format("started a process: %d", event.getProcessInstanceKey()));
        return event.getProcessInstanceKey();
    }
}
