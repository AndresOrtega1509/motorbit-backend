package com.motorbit.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(
        @NotBlank(message = "El nombre es obligatorio.")
        @Size(max = 100, message = "El nombre no debe superar los 100 caracteres.")
        String nombre,

        @NotBlank(message = "La cédula es obligatoria.")
        @Pattern(regexp = "\\d{6,15}", message = "La cédula debe contener únicamente números y tener entre 6 y 15 dígitos.")
        String cedula,

        @NotBlank(message = "El teléfono es obligatorio.")
        @Pattern(regexp = "\\d{7,15}", message = "El teléfono debe contener únicamente números y tener entre 7 y 15 dígitos.")
        String telefono,

        @Email(message = "El email debe tener un formato válido.")
        @Size(max = 100, message = "El email no debe superar los 100 caracteres.")
        String email
    ) 
{}