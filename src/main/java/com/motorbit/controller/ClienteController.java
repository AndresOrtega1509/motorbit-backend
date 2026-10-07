package com.motorbit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.motorbit.dto.request.ClienteRequest;
import com.motorbit.dto.response.ClienteResponse;
import com.motorbit.response.ApiResponse;
import com.motorbit.service.IClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {
	private final IClienteService clienteService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponse>> registrar(
            @Valid @RequestBody ClienteRequest clienteRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(
                        "Cliente registrado correctamente.",
                        clienteService.registrar(clienteRequest)
                ));
    }

	@GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponse>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Clientes obtenidos correctamente.",
                        clienteService.listar()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Cliente obtenido correctamente.",
                        clienteService.buscarPorId(id)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest clienteRequest) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Cliente actualizado correctamente.",
                        clienteService.actualizar(id, clienteRequest)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		clienteService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}