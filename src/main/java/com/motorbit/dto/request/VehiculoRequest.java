package com.motorbit.dto.request;

import com.motorbit.validators.AnioVehiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record VehiculoRequest(
        @NotBlank(message = "La placa es obligatoria.")
        @Pattern(regexp = "(?i)^[A-Z]{3}\\d{3}$", message = "La placa debe tener 3 letras seguidas de 3 números, por ejemplo ABC123.")
        String placa,

        @NotBlank(message = "La marca es obligatoria.")
        @Size(max = 50, message = "La marca no debe superar los 50 caracteres.")
        String marca,

        @NotBlank(message = "El modelo es obligatorio.")
        @Size(max = 50, message = "El modelo no debe superar los 50 caracteres.")
        String modelo,

        @NotNull(message = "El año del vehículo es obligatorio.")
        @AnioVehiculo
        Integer anio,

        @NotNull(message = "El ID del cliente es obligatorio.")
        @Positive(message = "El ID del cliente debe ser un número positivo.")
        Long clienteId
    ) 
{}