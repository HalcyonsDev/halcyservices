package com.halcyon.customer.service;

import com.halcyon.amqp.RabbitMQConfig;
import com.halcyon.amqp.RabbitMQMessageProvider;
import com.halcyon.customer.model.Customer;
import com.halcyon.customer.model.CustomerRegistrationRequest;
import com.halcyon.customer.model.FraudCheckResponse;
import com.halcyon.customer.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    private final ICustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final RabbitMQMessageProvider rabbitMQMessageProvider;

    public void register(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // TODO: check if email valid
        // TODO: check if email not taken
        customerRepository.saveAndFlush(customer);
        // TODO: check if fraudster

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                    FraudCheckResponse.class,
                    customer.getId()
        );

        assert fraudCheckResponse != null;
        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        rabbitMQMessageProvider.publish(
                "Registering message, hehe",
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY
        );
    }
}