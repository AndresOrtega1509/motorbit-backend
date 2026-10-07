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

import com.motorbit.dto.request.VehiculoRequest;
import com.motorbit.dto.response.VehiculoResponse;
import com.motorbit.response.ApiResponse;
import com.motorbit.service.IVehiculoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {
	private final IVehiculoService vehiculoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<VehiculoResponse>> registrar(
            @Valid @RequestBody VehiculoRequest vehiculoRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(
                        "Vehículo registrado correctamente.",
                        vehiculoService.registrar(vehiculoRequest)
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehiculoResponse>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Vehículos obtenidos correctamente.",
                        vehiculoService.listar()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehiculoResponse>> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Vehículo obtenido correctamente.",
                        vehiculoService.buscarPorId(id)
                )
        );
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<ApiResponse<List<VehiculoResponse>>> listarPorCliente(
            @PathVariable Long clienteId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Vehículos del cliente obtenidos correctamente.",
                        vehiculoService.listarPorCliente(clienteId)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehiculoResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VehiculoRequest vehiculoRequest) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Vehículo actualizado correctamente.",
                        vehiculoService.actualizar(id, vehiculoRequest)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		vehiculoService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}