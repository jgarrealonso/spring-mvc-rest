package com.dioler.springmvcrest.api.v1.model;

import lombok.Data;

@Data
public class CustomerDTO {

    Long id;
    String firstName;
    String lastName;
    String customerUrl;
}
