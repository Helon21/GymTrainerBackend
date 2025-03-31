package com.example.GymTrainer.controller;

import com.example.GymTrainer.model.Customer;
import com.example.GymTrainer.model.dto.CustomerRequestDTO;
import com.example.GymTrainer.model.dto.CustomerResponseDTO;
import com.example.GymTrainer.model.dto.mapper.CustomerMapper;
import com.example.GymTrainer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private CustomerService customerService;
    private CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable UUID id) {
        Customer customer = customerService.findCustomerById(id);
        CustomerResponseDTO customerResponseDTO = customerMapper.toResponseDTO(customer);
        return ResponseEntity.ok(customerResponseDTO);
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerMapper.toEntity(customerRequestDTO);
        Customer createdCustomer = customerService.createCustomer(customer);
        CustomerResponseDTO customerResponseDTO = customerMapper.toResponseDTO(createdCustomer);
        return ResponseEntity.status(201).body(customerResponseDTO);
    }

    @PutMapping
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerMapper.toEntity(customerRequestDTO);
        Customer updatedCustomer = customerService.updateCustomer(customer);
        CustomerResponseDTO customerResponseDTO = customerMapper.toResponseDTO(updatedCustomer);
        return ResponseEntity.ok(customerResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
