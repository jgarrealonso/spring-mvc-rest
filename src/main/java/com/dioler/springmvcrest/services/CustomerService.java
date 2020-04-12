package com.dioler.springmvcrest.services;

import com.dioler.springmvcrest.api.v1.model.CustomerDTO;
import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();

    Optional<CustomerDTO> getCustomerById(Long id);

    CustomerDTO createCustomer(CustomerDTO customerDTO);

    CustomerDTO saveCustomer(Long id, CustomerDTO customerDTO);

    Optional<CustomerDTO> updateCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
