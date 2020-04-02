package com.dioler.springmvcrest.api.v1.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.dioler.springmvcrest.api.v1.model.CustomerDTO;
import com.dioler.springmvcrest.domain.Customer;
import org.junit.jupiter.api.Test;

public class CustomerMapperTest {

    public static final String FIRST_NAME = "Ven";
    public static final String LAST_NAME = "Y Pintame";
    CustomerMapper customerMapper=CustomerMapper.INSTANCE;
    @Test
    void customerToCustomerDTO() {
        Customer customer=Customer.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();

        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        assertEquals(FIRST_NAME, customerDTO.getFirstName());
        assertEquals(LAST_NAME, customerDTO.getLastName());
    }
}