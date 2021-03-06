package com.dioler.springmvcrest.services;

import com.dioler.springmvcrest.api.v1.mapper.CustomerMapper;
import com.dioler.springmvcrest.api.v1.model.CustomerDTO;
import com.dioler.springmvcrest.domain.Customer;
import com.dioler.springmvcrest.repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    public static final String URI = "/api/v1/customers/";
    private final CustomerRepository customerRepository;
    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
            .map(customer -> {
                CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                customerDTO.setCustomerUrl(URI + customer.getId());
                return customerDTO;
            })
            .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id)
            .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return save(customerDTO);
    }

    @Override
    public CustomerDTO saveCustomer(Long id, CustomerDTO customerDTO) {
        customerDTO.setId(id);
        return save(customerDTO);
    }

    private CustomerDTO save(CustomerDTO customerDTO) {
        Customer newCustomer = customerRepository.save(customerMapper.customerDTOToCustomer(customerDTO));
        CustomerDTO newCustomerDTO = customerMapper.customerToCustomerDTO(newCustomer);
        newCustomerDTO.setCustomerUrl(URI + newCustomer.getId());
        return newCustomerDTO;
    }

    @Override
    public Optional<CustomerDTO> updateCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
            .map(customer -> {
                Optional.ofNullable(customerDTO.getFirstName())
                    .ifPresent(customer::setFirstName);
                Optional.ofNullable(customerDTO.getLastName())
                    .ifPresent(customer::setFirstName);
                return customerRepository.save(customer);
            })
            .map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
