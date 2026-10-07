package com.motorbit.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.motorbit.dto.request.VehiculoRequest;
import com.motorbit.dto.response.VehiculoResponse;
import com.motorbit.exception.RecursoExistenteException;
import com.motorbit.exception.RecursoNoEncontradoException;
import com.motorbit.mapper.VehiculoMapper;
import com.motorbit.model.Cliente;
import com.motorbit.model.Vehiculo;
import com.motorbit.repository.ClienteRepository;
import com.motorbit.repository.VehiculoRepository;
import com.motorbit.service.IVehiculoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service
@RequiredArgsConstructor
@Transactional
public class VehiculoServiceImpl implements IVehiculoService {
	private final VehiculoRepository vehiculoRepository;
	private final ClienteRepository clienteRepository;
	private final VehiculoMapper vehiculoMapper;

	@Override
	public VehiculoResponse registrar(VehiculoRequest vehiculoRequest) {
		String placa = normalizarPlaca(vehiculoRequest.placa());

		log.debug("Iniciando registro de vehículo con placa {}", placa);

		if (vehiculoRepository.existsByPlaca(placa)) {
            throw new RecursoExistenteException(
                    "Vehículo",
                    "placa",
                    placa
            );
        }

		Cliente cliente = buscarCliente(vehiculoRequest.clienteId());

		Vehiculo vehiculo = vehiculoMapper.toEntity(vehiculoRequest);

		vehiculo.setPlaca(placa);
		vehiculo.setCliente(cliente);

		Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);

		log.info(
                "Vehículo registrado correctamente con id {} y placa {}",
                vehiculoGuardado.getId(),
                vehiculoGuardado.getPlaca()
        );

		return vehiculoMapper.toResponse(vehiculoGuardado);
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehiculoResponse> listar() {
		log.debug("Consultando listado de vehículos");

		return vehiculoRepository.findAll()
				.stream()
				.map(vehiculoMapper::toResponse)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public VehiculoResponse buscarPorId(Long id) {
		log.debug("Buscando vehículo con id {}", id);

		return vehiculoMapper.toResponse(buscarVehiculo(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<VehiculoResponse> listarPorCliente(Long clienteId) {
		log.debug("Consultando vehículos asociados al cliente con id {}", clienteId);

		buscarCliente(clienteId);

		return vehiculoRepository.findByCliente_Id(clienteId)
				.stream()
				.map(vehiculoMapper::toResponse)
				.toList();
	}

	@Override
	public VehiculoResponse actualizar(Long id, VehiculoRequest vehiculoRequest) {
		log.debug("Iniciando actualización del vehículo con id {}", id);

		Vehiculo vehiculo = buscarVehiculo(id);

		String placa = normalizarPlaca(vehiculoRequest.placa());

		if (!placa.equalsIgnoreCase(vehiculo.getPlaca()) && vehiculoRepository.existsByPlaca(placa)) {
			throw new RecursoExistenteException("Vehículo", "placa", placa);
		}

		Cliente cliente = buscarCliente(vehiculoRequest.clienteId());

		vehiculoMapper.actualizarEntidad(vehiculoRequest, vehiculo);

		vehiculo.setPlaca(placa);
		vehiculo.setCliente(cliente);

		Vehiculo vehiculoActualizado = vehiculoRepository.save(vehiculo);

		log.info(
                "Vehículo actualizado correctamente con id {}",
                vehiculoActualizado.getId()
        );

		return vehiculoMapper.toResponse(vehiculoActualizado);
	}

	@Override
	public void eliminar(Long id) {
		vehiculoRepository.delete(buscarVehiculo(id));

		log.info("Vehículo eliminado correctamente con id {}", id);
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

	private Cliente buscarCliente(Long id) {
		return clienteRepository.findById(id)
				.orElseThrow(() -> 
								new RecursoNoEncontradoException(
                            		"Cliente",
                            		"id",
                            		id
                    			)
                			);
	}

	private String normalizarPlaca(String placa) {
		return placa.toUpperCase(Locale.ROOT);
	}
}