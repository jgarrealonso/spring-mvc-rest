package com.dioler.springmvcrest.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dioler.springmvcrest.api.v1.model.CategoryDTO;
import com.dioler.springmvcrest.api.v1.model.CustomerDTO;
import com.dioler.springmvcrest.services.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class CustomerControllerTest {

    @Mock
    CustomerService customerService;
    @InjectMocks
    CustomerController customerController;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getCustomers() throws Exception {
        //Given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setFirstName("Pepe");
        customer1.setLastName("Paquete");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(2L);
        customer2.setFirstName("Pepa");
        customer2.setLastName("Paqueta");

        List<CustomerDTO> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        //When
        mockMvc.perform(get("/api/v1/customers")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.customers", hasSize(2)));

    }

    @Test
    void getCustomerById() throws Exception {
        //Given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(1L);
        customer1.setFirstName("Pepe");
        customer1.setLastName("Paquete");


        when(customerService.getCustomerById(any())).thenReturn(Optional.of(customer1));

        mockMvc.perform(
            get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName",equalTo("Pepe")));
    }

    @Test
    void getNotFoundWhenCategoryNotExisting() throws Exception {
        when(customerService.getCustomerById(any())).thenReturn(Optional.empty());
        mockMvc.perform(
            get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void getCreatedWhenPostIsOk() throws Exception {
      CustomerDTO customerDTOIn= new CustomerDTO();
      customerDTOIn.setId(1L);
      customerDTOIn.setFirstName("Jorge");
      customerDTOIn.setLastName("Garre");

      CustomerDTO customerDTOOut = new CustomerDTO();
      customerDTOOut.setId(customerDTOIn.getId());
      customerDTOOut.setFirstName(customerDTOIn.getFirstName());
      customerDTOOut.setLastName(customerDTOIn.getLastName());
      customerDTOOut.setCustomerUrl("/api/v1/customers/"+customerDTOIn.getId());

       when(customerService.createCustomer(customerDTOIn)).thenReturn(customerDTOOut);
        mockMvc.perform(
            post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customerDTOIn)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.firstName",equalTo("Jorge")))
            .andExpect(jsonPath("$.customerUrl",equalTo("/api/v1/customers/1")));

    }

    @Test
    void getCreatedWhenPutIsOk() throws Exception {
        CustomerDTO customerDTOIn= new CustomerDTO();
        customerDTOIn.setId(1L);
        customerDTOIn.setFirstName("Jorge");
        customerDTOIn.setLastName("Garre");

        CustomerDTO customerDTOOut = new CustomerDTO();
        customerDTOOut.setId(customerDTOIn.getId());
        customerDTOOut.setFirstName(customerDTOIn.getFirstName());
        customerDTOOut.setLastName(customerDTOIn.getLastName());
        customerDTOOut.setCustomerUrl("/api/v1/customers/"+customerDTOIn.getId());

        when(customerService.createCustomer(customerDTOIn)).thenReturn(customerDTOOut);
        MvcResult perform = mockMvc.perform(
            put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customerDTOIn)))
                .andExpect(status().isOk())
            .andReturn();
        System.out.println(perform.getResponse().getContentAsString());
    }
    @Test
    void deleteById() throws Exception {
       mockMvc
       .perform(delete("/api/v1/customers/1")
           .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk());
    }
}