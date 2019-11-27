package com.moreti.kmoretimsscbreweryclient.web.client;

import com.moreti.kmoretimsscbreweryclient.web.model.CustomerDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value = "km.brewery")
public class CustomerClient {
    private String customerEndpoint;
    private final RestTemplate restTemplate;

    public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setCustomerEndpoint(String customerEndpoint) {
        this.customerEndpoint = customerEndpoint;
    }

    public CustomerDto getCustomerById(UUID uuid) {
        return restTemplate.getForObject(customerEndpoint + "/" + uuid, CustomerDto.class);
    }

    public URI saveNewCustomer(CustomerDto customerDto) {
        return restTemplate.postForLocation(customerEndpoint, customerDto);
    }

    public void updateCustomer(UUID uuid, CustomerDto customerDto) {
        restTemplate.put(customerEndpoint + "/" + uuid, customerDto);
    }

    public void deleteCustomer(UUID uuid) {
        restTemplate.delete(customerEndpoint + "/" + uuid);
    }
}
