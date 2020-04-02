package com.dioler.springmvcrest.api.v1.model;

import java.util.List;
import lombok.Data;

@Data
public class CustomersDTO {

    private List<CustomerDTO> customers;
}
