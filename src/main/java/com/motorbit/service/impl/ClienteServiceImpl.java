package com.motorbit.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.motorbit.dto.request.ClienteRequest;
import com.motorbit.dto.response.ClienteResponse;
import com.motorbit.exception.RecursoExistenteException;
import com.motorbit.exception.RecursoNoEncontradoException;
import com.motorbit.mapper.ClienteMapper;
import com.motorbit.model.Cliente;
import com.motorbit.repository.ClienteRepository;
import com.motorbit.service.IClienteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j 
@Service
@RequiredArgsConstructor 
@Transactional 
public class ClienteServiceImpl implements IClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public ClienteResponse registrar(ClienteRequest clienteRequest) {
        String cedula = clienteRequest.cedula();

        log.debug("Iniciando registro de cliente con cédula {}", cedula);

        if (clienteRepository.existsByCedula(cedula)) {
            throw new RecursoExistenteException("Cliente", "cédula", cedula);
        }

        Cliente clienteCreado = clienteMapper.toEntity(clienteRequest);

        Cliente clienteGuardado = clienteRepository.save(clienteCreado);

        log.info(
                "Cliente registrado correctamente con id {}",
                clienteGuardado.getId()
        );

        return clienteMapper.toResponse(clienteGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        log.debug("Consultando listado de clientes");

        return clienteRepository.findAll()
                                .stream()
                                .map(clienteMapper::toResponse)
                                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(Long id) {
        log.debug("Buscando cliente con id {}", id);

        return clienteMapper.toResponse(buscarCliente(id));
    }

    @Override
    public ClienteResponse actualizar(Long id, ClienteRequest clienteRequest) {
        log.debug("Iniciando actualización del cliente con id {}", id);

        Cliente clienteEncontrado = buscarCliente(id);

        if (!clienteRequest.cedula().equals(clienteEncontrado.getCedula()) && 
                                                clienteRepository.existsByCedula(clienteRequest.cedula())) {
            throw new RecursoExistenteException("Cliente", "cédula", clienteRequest.cedula());
        }

        clienteMapper.actualizarEntidad(clienteRequest, clienteEncontrado);
        
        Cliente clienteActualizado = clienteRepository.save(clienteEncontrado);

        log.info(
                "Cliente actualizado correctamente con id {}",
                clienteActualizado.getId()
        );

        return clienteMapper.toResponse(clienteActualizado);
    }

    @Override
    public void eliminar(Long id) {
        clienteRepository.delete(buscarCliente(id));

        log.info("Cliente eliminado correctamente con id {}", id);
    }

    private Cliente buscarCliente(Long id) {
        return clienteRepository.findById(id)
                                .orElseThrow(() -> 
                                                new RecursoNoEncontradoException(
                                                    "Cliente", 
                                                    "id", 
                                                    id)
                                            );
    }
}