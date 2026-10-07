package com.motorbit.dto.request;

import com.motorbit.model.enums.EstadoOrden;

import jakarta.validation.constraints.NotNull;

public record CambioEstadoOrdenRequest(
    @NotNull(message = "El nuevo estado de la orden es obligatorio.")
    EstadoOrden estado
) {}
