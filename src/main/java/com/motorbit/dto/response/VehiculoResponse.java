package com.motorbit.dto.response;

public record VehiculoResponse(
        Long id,
        String placa,
        String marca,
        String modelo,
        Integer anio,
        Long clienteId
) {}