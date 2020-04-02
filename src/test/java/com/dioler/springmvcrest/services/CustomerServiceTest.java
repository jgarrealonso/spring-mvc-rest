package com.dioler.springmvcrest.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.dioler.springmvcrest.api.v1.model.CustomerDTO;
import com.dioler.springmvcrest.domain.Customer;
import com.dioler.springmvcrest.repositories.CustomerRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CustomerServiceTest {

    public static final String NAME_1 = "Name 1";
    public static final String SURNAME_1 = "Surname 1";
    public static final String NAME_2 = "Name 2";
    public static final String SURNAME_2 = "Surname 2";
    public static final long ID_1 = 71L;
    public static final long ID_2 = 72L;
    public static final String URI = "/api/v1/customers/";

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCustomers() {
        //Given
        List<Customer> customers = Arrays.asList(
            Customer.builder().id(ID_1).firstName(NAME_1).lastName(SURNAME_1).build(),
            Customer.builder().id(ID_2).firstName(NAME_2).lastName(SURNAME_2).build()
        );
        when(customerRepository.findAll()).thenReturn(customers);

        //When
        List<CustomerDTO> customersDTO = customerService.getAllCustomers();

        //Then
        assertEquals(2, customersDTO.size());

        assertEquals(NAME_1, customersDTO.get(0).getFirstName());
        assertEquals(SURNAME_2, customersDTO.get(1).getLastName());
        assertEquals(URI + ID_1, customersDTO.get(0).getCustomerUrl());

    }

    @Test
    void getCustomerById() {
        //Given

        Customer customer = Customer.builder().id(ID_1).firstName(NAME_1).lastName(SURNAME_1).build();
        when(customerRepository.findById(ID_1)).thenReturn(Optional.ofNullable(customer));

        //When
        CustomerDTO customerDTO = customerService.getCustomerById(ID_1).get();

        //Then
        assertEquals(ID_1, customerDTO.getId());
        assertEquals(NAME_1, customerDTO.getFirstName());
        assertEquals(SURNAME_1, customerDTO.getLastName());
    }


    @Test
    void createCustomer() {
        //Given
        Customer savedCustomer = Customer.builder().id(ID_1).firstName(NAME_1).lastName(SURNAME_1).build();

        CustomerDTO newCustomerDTO = new CustomerDTO();
        newCustomerDTO.setFirstName(NAME_1);
        newCustomerDTO.setLastName(SURNAME_1);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        //When
        CustomerDTO savedCustomerDTO = customerService.createCustomer(newCustomerDTO);

        //Then
        assertEquals(NAME_1, savedCustomerDTO.getFirstName());
        assertEquals(SURNAME_1, savedCustomerDTO.getLastName());
    }

    @Test
    void replaceCustomer() {
        //Given
        CustomerDTO customerToReplaceDTO = new CustomerDTO();
        customerToReplaceDTO.setFirstName(NAME_1);
        customerToReplaceDTO.setLastName(SURNAME_1);

        Customer customerReplaced = Customer.builder().id(ID_2).firstName(NAME_2).lastName(SURNAME_2).build();


        when(customerRepository.save(any(Customer.class))).thenReturn(customerReplaced);

        //When
        CustomerDTO customerReplacedDTO = customerService.saveCustomer(ID_1, customerToReplaceDTO);

        //Then
        assertEquals(ID_2, customerReplacedDTO.getId());
        assertEquals(NAME_2, customerReplacedDTO.getFirstName());
        assertEquals(SURNAME_2, customerReplacedDTO.getLastName());
    }
}