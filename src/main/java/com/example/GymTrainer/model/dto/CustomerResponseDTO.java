package com.example.GymTrainer.model.dto;

import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String name,
        String email,
        String specialities
) {
}
