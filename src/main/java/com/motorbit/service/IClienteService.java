package com.motorbit.service;

import java.util.List;

import com.motorbit.dto.request.ClienteRequest;
import com.motorbit.dto.response.ClienteResponse;

public interface IClienteService {

    ClienteResponse registrar(ClienteRequest clienteRequest);
    List<ClienteResponse> listar();
    ClienteResponse buscarPorId(Long id);
    ClienteResponse actualizar(Long id, ClienteRequest clienteRequest);
    void eliminar(Long id);
}