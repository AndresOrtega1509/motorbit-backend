package com.motorbit.service;

import java.util.List;

import com.motorbit.dto.request.VehiculoRequest;
import com.motorbit.dto.response.VehiculoResponse;

public interface IVehiculoService {
	VehiculoResponse registrar(VehiculoRequest vehiculoRequest);

	List<VehiculoResponse> listar();

	VehiculoResponse buscarPorId(Long id);

	List<VehiculoResponse> listarPorCliente(Long clienteId);

	VehiculoResponse actualizar(Long id, VehiculoRequest vehiculoRequest);

	void eliminar(Long id);
}