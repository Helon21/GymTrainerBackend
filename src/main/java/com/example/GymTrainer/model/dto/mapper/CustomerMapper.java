// src/main/java/com/example/GymTrainer/model/dto/mapper/CustomerMapper.java
package com.example.GymTrainer.model.dto.mapper;

import com.example.GymTrainer.model.Customer;
import com.example.GymTrainer.model.dto.CustomerRequestDTO;
import com.example.GymTrainer.model.dto.CustomerResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    private final ModelMapper modelMapper;

    public CustomerMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CustomerResponseDTO toResponseDTO(Customer customer) {
        return modelMapper.map(customer, CustomerResponseDTO.class);
    }

    public Customer toEntity(CustomerRequestDTO customerRequestDTO) {
        return modelMapper.map(customerRequestDTO, Customer.class);
    }
}