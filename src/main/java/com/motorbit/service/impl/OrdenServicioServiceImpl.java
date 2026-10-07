package com.motorbit.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.motorbit.dto.request.CambioEstadoOrdenRequest;
import com.motorbit.dto.request.OrdenServicioRequest;
import com.motorbit.dto.response.OrdenServicioResponse;
import com.motorbit.exception.RecursoNoEncontradoException;
import com.motorbit.exception.TransicionEstadoInvalidaException;
import com.motorbit.mapper.OrdenServicioMapper;
import com.motorbit.model.OrdenServicio;
import com.motorbit.model.Vehiculo;
import com.motorbit.model.enums.EstadoOrden;
import com.motorbit.repository.OrdenServicioRepository;
import com.motorbit.repository.VehiculoRepository;
import com.motorbit.service.IOrdenServicioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service
@RequiredArgsConstructor
@Transactional
public class OrdenServicioServiceImpl implements IOrdenServicioService {
	private final OrdenServicioRepository ordenServicioRepository;
	private final VehiculoRepository vehiculoRepository;
	private final OrdenServicioMapper ordenServicioMapper;

	@Override
	public OrdenServicioResponse registrar(OrdenServicioRequest ordenServicioRequest) {
		Long vehiculoId = ordenServicioRequest.vehiculoId();

		log.debug("Iniciando registro de orden de servicio para vehículo con id {}", vehiculoId);

		Vehiculo vehiculo = buscarVehiculo(vehiculoId);

		OrdenServicio ordenServicio = ordenServicioMapper.toEntity(ordenServicioRequest);

		ordenServicio.setVehiculo(vehiculo);
		ordenServicio.setFechaIngreso(LocalDateTime.now());

		OrdenServicio ordenGuardada = ordenServicioRepository.save(ordenServicio);

		log.info(
                "Orden de servicio registrada correctamente con id {} para vehículo id {}",
                ordenGuardada.getId(),
                vehiculoId
        );

		return ordenServicioMapper.toResponse(ordenServicioRepository.save(ordenServicio));
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrdenServicioResponse> listar() {
		log.debug("Consultando listado de órdenes de servicio");

		return ordenServicioRepository.findAll()
				.stream()
				.map(ordenServicioMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public OrdenServicioResponse buscarPorId(Long id) {
		log.debug("Buscando orden de servicio con id {}", id);

		return ordenServicioMapper.toResponse(buscarOrdenServicio(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrdenServicioResponse> listarPorVehiculo(Long vehiculoId) {
		log.debug("Consultando órdenes de servicio del vehículo con id {}", vehiculoId);

		buscarVehiculo(vehiculoId);

		return ordenServicioRepository.findByVehiculo_Id(vehiculoId)
				.stream()
				.map(ordenServicioMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<OrdenServicioResponse> listarPorEstado(EstadoOrden estado) {
		log.debug("Consultando órdenes de servicio con estado {}", estado);

		return ordenServicioRepository.findByEstado(estado)
				.stream()
				.map(ordenServicioMapper::toResponse)
				.toList();
	}

	@Override
	public OrdenServicioResponse actualizar(Long id, OrdenServicioRequest ordenServicioRequest) {
		log.debug("Iniciando actualización de orden de servicio con id {}", id);

		OrdenServicio ordenServicio = buscarOrdenServicio(id);
		Vehiculo vehiculo = buscarVehiculo(ordenServicioRequest.vehiculoId());

		ordenServicioMapper.actualizarEntidad(ordenServicioRequest, ordenServicio);
		ordenServicio.setVehiculo(vehiculo);

		OrdenServicio ordenActualizada = ordenServicioRepository.save(ordenServicio);

		log.info(
                "Orden de servicio actualizada correctamente con id {}",
                ordenActualizada.getId()
        );

		return ordenServicioMapper.toResponse(ordenActualizada);
	}

	@Override
	public OrdenServicioResponse cambiarEstado(Long id, CambioEstadoOrdenRequest estadoOrdenRequest) {
        OrdenServicio ordenServicio = buscarOrdenServicio(id);

        EstadoOrden estadoActual = ordenServicio.getEstado();
        EstadoOrden nuevoEstado = estadoOrdenRequest.estado();

		log.debug(
                "Intentando cambiar estado de orden {} de {} a {}",
                id,
                estadoActual,
                nuevoEstado
        );

        boolean transicionValida =
                (estadoActual == EstadoOrden.RECIBIDO && nuevoEstado == EstadoOrden.EN_PROGRESO) ||
                (estadoActual == EstadoOrden.EN_PROGRESO && nuevoEstado == EstadoOrden.FINALIZADO) ||
                (estadoActual == EstadoOrden.FINALIZADO && nuevoEstado == EstadoOrden.ENTREGADO);

        if (!transicionValida) {
            throw new TransicionEstadoInvalidaException(estadoActual, nuevoEstado);
        }

        ordenServicio.setEstado(nuevoEstado);

        if (nuevoEstado == EstadoOrden.FINALIZADO) {
            ordenServicio.setFechaFinalizacion(LocalDateTime.now());
        }

        if (nuevoEstado == EstadoOrden.ENTREGADO) {
            ordenServicio.setFechaEntrega(LocalDateTime.now());
        }

        OrdenServicio ordenActualizada = ordenServicioRepository.save(ordenServicio);

		log.info(
                "Orden de servicio {} cambió de estado {} a {}",
                id,
                estadoActual,
                nuevoEstado
        );

        return ordenServicioMapper.toResponse(ordenActualizada);
	}

	@Override
	public void eliminar(Long id) {
		ordenServicioRepository.delete(buscarOrdenServicio(id));

		log.info("Orden de servicio eliminada correctamente con id {}", id);
	}

	private OrdenServicio buscarOrdenServicio(Long id) {
		return ordenServicioRepository.findById(id)
				.orElseThrow(() ->
								new RecursoNoEncontradoException(
										"Orden de servicio",
										"id",
										id
									)
                			);
	}

	private Vehiculo buscarVehiculo(Long id) {
		return vehiculoRepository.findById(id)
				.orElseThrow(() -> 
								new RecursoNoEncontradoException(
										"Vehículo",
										"id",
										id
									)
                			);
	}
}