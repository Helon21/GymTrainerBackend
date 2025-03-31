// src/test/java/com/example/GymTrainer/unit_tests/CustomerControllerTest.java
package com.example.GymTrainer.unit_tests;

import com.example.GymTrainer.consts.TestConstants;
import com.example.GymTrainer.controller.CustomerController;
import com.example.GymTrainer.model.Customer;
import com.example.GymTrainer.model.dto.CustomerRequestDTO;
import com.example.GymTrainer.model.dto.CustomerResponseDTO;
import com.example.GymTrainer.model.dto.mapper.CustomerMapper;
import com.example.GymTrainer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerUnitTests {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerController customerController;

    private CustomerRequestDTO customerRequestDTO;
    private Customer customer;
    private CustomerResponseDTO customerResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customerRequestDTO = new CustomerRequestDTO(
                TestConstants.CUSTOMER_NAME,
                TestConstants.CUSTOMER_EMAIL,
                TestConstants.CUSTOMER_PASSWORD,
                TestConstants.CUSTOMER_SPECIALITIES
        );
        customer = new Customer(
                null,
                TestConstants.CUSTOMER_NAME,
                TestConstants.CUSTOMER_EMAIL,
                TestConstants.CUSTOMER_PASSWORD,
                TestConstants.CUSTOMER_SPECIALITIES
        );
        customerResponseDTO = new CustomerResponseDTO(
                null,
                TestConstants.CUSTOMER_NAME,
                TestConstants.CUSTOMER_EMAIL,
                TestConstants.CUSTOMER_SPECIALITIES
        );

        when(customerMapper.toEntity(any(CustomerRequestDTO.class))).thenReturn(customer);
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);
        when(customerMapper.toResponseDTO(any(Customer.class))).thenReturn(customerResponseDTO);
    }

    @Test
    public void testCreateCustomer() {
        ResponseEntity<CustomerResponseDTO> response = customerController.createCustomer(customerRequestDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(customerResponseDTO, response.getBody());
    }
}