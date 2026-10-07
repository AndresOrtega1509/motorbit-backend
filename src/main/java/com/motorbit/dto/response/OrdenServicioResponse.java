package com.motorbit.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.motorbit.model.enums.EstadoOrden;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrdenServicioResponse(
        Long id,
        String descripcion,
        LocalDateTime fechaIngreso,
        LocalDateTime fechaFinalizacion,
        LocalDateTime fechaEntrega,
        EstadoOrden estado,
        BigDecimal costo,
        Long vehiculoId
) {}