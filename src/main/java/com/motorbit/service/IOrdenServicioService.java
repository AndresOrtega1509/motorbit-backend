package com.motorbit.service;

import java.util.List;

import com.motorbit.dto.request.CambioEstadoOrdenRequest;
import com.motorbit.dto.request.OrdenServicioRequest;
import com.motorbit.dto.response.OrdenServicioResponse;
import com.motorbit.model.enums.EstadoOrden;

public interface IOrdenServicioService {
	OrdenServicioResponse registrar(OrdenServicioRequest ordenServicioRequest);

	List<OrdenServicioResponse> listar();

	OrdenServicioResponse buscarPorId(Long id);

	List<OrdenServicioResponse> listarPorVehiculo(Long vehiculoId);

	List<OrdenServicioResponse> listarPorEstado(EstadoOrden estado);

	OrdenServicioResponse actualizar(Long id, OrdenServicioRequest ordenServicioRequest);

	OrdenServicioResponse cambiarEstado(Long id, CambioEstadoOrdenRequest estadoOrdenRequest);

	void eliminar(Long id);
}