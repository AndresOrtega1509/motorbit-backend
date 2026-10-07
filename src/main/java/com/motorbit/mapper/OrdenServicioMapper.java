package com.motorbit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.motorbit.dto.request.OrdenServicioRequest;
import com.motorbit.dto.response.OrdenServicioResponse;
import com.motorbit.model.OrdenServicio;

@Mapper(componentModel = "spring")
public interface OrdenServicioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaIngreso", ignore = true)
    @Mapping(target = "fechaFinalizacion", ignore = true)
    @Mapping(target = "fechaEntrega", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "vehiculo", ignore = true)
    OrdenServicio toEntity(OrdenServicioRequest ordenServicioRequest);

    @Mapping(target = "vehiculoId", source = "vehiculo.id")
    OrdenServicioResponse toResponse(OrdenServicio ordenServicio);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaIngreso", ignore = true)
    @Mapping(target = "fechaFinalizacion", ignore = true)
    @Mapping(target = "fechaEntrega", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "vehiculo", ignore = true)
    void actualizarEntidad(
            OrdenServicioRequest ordenServicioRequest,
            @MappingTarget OrdenServicio ordenServicio
    );
}