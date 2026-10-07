package com.motorbit.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record OrdenServicioRequest(
        @NotBlank(message = "La descripción es obligatoria.")
        @Size(max = 500, message = "La descripción no debe superar los 500 caracteres.")
        String descripcion,

        @NotNull(message = "El costo de la orden es obligatorio.")
        @DecimalMin(value = "0.0", inclusive = true, message = "El costo no puede ser negativo.")
        @Digits(integer = 10, fraction = 2, message = "El costo debe tener hasta 10 dígitos enteros y 2 decimales.")
        BigDecimal costo,

        @NotNull(message = "El ID del vehículo es obligatorio.")
        @Positive(message = "El ID del vehículo debe ser un número positivo.")
        Long vehiculoId
    ) 
{}