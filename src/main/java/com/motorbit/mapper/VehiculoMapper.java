package com.motorbit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.motorbit.dto.request.VehiculoRequest;
import com.motorbit.dto.response.VehiculoResponse;
import com.motorbit.model.Vehiculo;

@Mapper(componentModel = "spring")
public interface VehiculoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "ordenesServicio", ignore = true)
    Vehiculo toEntity(VehiculoRequest vehiculoRequest);

    @Mapping(target = "clienteId", source = "cliente.id")
    VehiculoResponse toResponse(Vehiculo vehiculo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "ordenesServicio", ignore = true)
    void actualizarEntidad(
            VehiculoRequest vehiculoRequest,
            @MappingTarget Vehiculo vehiculo
    );
}