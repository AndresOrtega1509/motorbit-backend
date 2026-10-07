package com.motorbit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.motorbit.dto.request.RegistroRequest;
import com.motorbit.dto.response.AuthResponse;
import com.motorbit.model.Usuario;
import com.motorbit.model.enums.Rol;

@Mapper(componentModel = "spring", imports = Rol.class)
public interface AuthMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rol", expression = "java(Rol.USER)")
    Usuario toEntity(RegistroRequest registroRequest);

    AuthResponse toResponse(String token);
}