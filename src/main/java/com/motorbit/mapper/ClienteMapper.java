package com.motorbit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.motorbit.dto.request.ClienteRequest;
import com.motorbit.dto.response.ClienteResponse;
import com.motorbit.model.Cliente;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehiculos", ignore = true)
    Cliente toEntity(ClienteRequest clienteRequest);

    ClienteResponse toResponse(Cliente cliente);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vehiculos", ignore = true)
    void actualizarEntidad(
            ClienteRequest clienteRequest,
            @MappingTarget Cliente cliente
    );
}
