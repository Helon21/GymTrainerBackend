// src/test/java/com/example/GymTrainer/integration_tests/CustomerIntegrationTest.java
package com.example.GymTrainer.integration_tests;

import com.example.GymTrainer.consts.TestConstants;
import com.example.GymTrainer.model.Customer;
import com.example.GymTrainer.model.dto.CustomerRequestDTO;
import com.example.GymTrainer.model.dto.CustomerResponseDTO;
import com.example.GymTrainer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO(
                TestConstants.CUSTOMER_NAME,
                TestConstants.CUSTOMER_EMAIL,
                TestConstants.CUSTOMER_PASSWORD,
                TestConstants.CUSTOMER_SPECIALITIES
        );

        MvcResult result = mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(TestConstants.CUSTOMER_NAME)))
                .andExpect(jsonPath("$.email", is(TestConstants.CUSTOMER_EMAIL)))
                .andExpect(jsonPath("$.specialities", is(TestConstants.CUSTOMER_SPECIALITIES)))
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        CustomerResponseDTO customerResponseDTO = new ObjectMapper().readValue(responseContent, CustomerResponseDTO.class);

        Customer createdCustomer = customerRepository.findById(customerResponseDTO.id()).orElse(null);
        assertNotNull(createdCustomer);
        assertEquals(TestConstants.CUSTOMER_NAME, createdCustomer.getName());
        assertEquals(TestConstants.CUSTOMER_EMAIL, createdCustomer.getEmail());
        assertEquals(TestConstants.CUSTOMER_PASSWORD, createdCustomer.getPassword());
        assertEquals(TestConstants.CUSTOMER_SPECIALITIES, createdCustomer.getSpecialities());
    }
}