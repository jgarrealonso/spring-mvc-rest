package com.dioler.springmvcrest.it;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dioler.springmvcrest.api.v1.model.CustomerDTO;
import com.dioler.springmvcrest.api.v1.model.CustomersDTO;
import com.dioler.springmvcrest.controllers.CustomerController;
import com.dioler.springmvcrest.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerControllerIT {

    private CustomersDTO customersDTO;
    private WebTestClient client;

    @Autowired
    private CustomerService customerService;

    private CustomerDTO customerDTO;


    @BeforeEach
    void beforeEach() {
        this.client = WebTestClient
            .bindToController(new CustomerController(customerService))
            .configureClient()
            .baseUrl("/api/v1/customers")
            .build();

        customersDTO = new CustomersDTO();
        customersDTO.setCustomers(customerService.getAllCustomers());

        customerDTO = customerService.getCustomerById(customersDTO.getCustomers().get(1).getId()).get();
    }


    @Test
    void testPostCustomer() {

        CustomerDTO newCustomerDTO = new CustomerDTO();

        newCustomerDTO.setFirstName("Benito");
        newCustomerDTO.setLastName("Camelas");

        EntityExchangeResult<CustomerDTO> customerDTOEntityExchangeResult = client
            .post()
            .bodyValue(newCustomerDTO)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(CustomerDTO.class)
            .returnResult();

        CustomerDTO createdCustomerDTO = customerDTOEntityExchangeResult.getResponseBody();

        assertNotNull(createdCustomerDTO.getId());
        assertNotNull(createdCustomerDTO.getCustomerUrl());
        assertEquals(createdCustomerDTO.getFirstName(), newCustomerDTO.getFirstName());
        assertEquals(createdCustomerDTO.getLastName(), newCustomerDTO.getLastName());

    }

    @Test
    void testGetAllCustomers() {
        client
            .get()
            .exchange()
            .expectStatus().isOk()
            .expectBody(CustomersDTO.class)
            .isEqualTo(customersDTO);
    }

    @Test
    void testGetCustomer() {
        client
            .get()
            .uri("/" + customerDTO.getId().toString())
            .exchange()
            .expectStatus().isOk()
            .expectBody(CustomerDTO.class)
            .isEqualTo(customerDTO);
    }
}