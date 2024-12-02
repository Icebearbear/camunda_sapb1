package io.camunda.demo.process_payments;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;

@Component
public class ChargeCreditCardWorker {

  private final static Logger LOG = LoggerFactory.getLogger(ChargeCreditCardWorker.class);

  @JobWorker(type = "charge-credit-card")
  public Map<String, Double> chargeCreditCard(@Variable(name = "totalWithTax") Double totalWithTax) {
    LOG.info("charging credit card: {}", totalWithTax);

    Map<String, Double> result = Map.of("amountCharged", totalWithTax);
    LOG.info("Job Completed, result: {}" , result);
    return result;
  }
}
