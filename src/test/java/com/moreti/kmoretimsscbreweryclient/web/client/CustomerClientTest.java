package com.moreti.kmoretimsscbreweryclient.web.client;

import com.moreti.kmoretimsscbreweryclient.web.model.CustomerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerClientTest {

    @Autowired
    private CustomerClient client;

    @Test
    void getCustomerById() {
        CustomerDto customerDto = client.getCustomerById(UUID.randomUUID());

        assertNotNull(customerDto);
    }

    @Test
    void saveNewCustomer() {
        //given
        CustomerDto customerDto = CustomerDto.builder().name("New Customer").build();

        //when
        URI uri = client.saveNewCustomer(customerDto);

        //then
        assertNotNull(uri);

        System.out.println(uri);
    }

    @Test
    void updateCustomer() {
        //given
        CustomerDto customerDto = CustomerDto.builder().name("New Customer").build();

        client.updateCustomer(UUID.randomUUID(), customerDto);
    }

    @Test
    void deleteCustomer() {
        client.deleteCustomer(UUID.randomUUID());
    }

}