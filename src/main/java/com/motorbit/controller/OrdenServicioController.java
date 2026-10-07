package com.motorbit.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.motorbit.dto.request.CambioEstadoOrdenRequest;
import com.motorbit.dto.request.OrdenServicioRequest;
import com.motorbit.dto.response.OrdenServicioResponse;
import com.motorbit.model.enums.EstadoOrden;
import com.motorbit.response.ApiResponse;
import com.motorbit.service.IOrdenServicioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ordenes-servicio")
@RequiredArgsConstructor
public class OrdenServicioController {
	private final IOrdenServicioService ordenServicioService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<OrdenServicioResponse>> registrar(
            @Valid @RequestBody OrdenServicioRequest ordenServicioRequest) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(
                        "Orden de servicio registrada correctamente.",
                        ordenServicioService.registrar(ordenServicioRequest)
                ));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrdenServicioResponse>>> listar() {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Órdenes de servicio obtenidas correctamente.",
                        ordenServicioService.listar()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrdenServicioResponse>> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Orden de servicio obtenida correctamente.",
                        ordenServicioService.buscarPorId(id)
                )
        );
    }

    @GetMapping("/vehiculo/{vehiculoId}")
    public ResponseEntity<ApiResponse<List<OrdenServicioResponse>>> listarPorVehiculo(
            @PathVariable Long vehiculoId) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Órdenes de servicio del vehículo obtenidas correctamente.",
                        ordenServicioService.listarPorVehiculo(vehiculoId)
                )
        );
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<ApiResponse<List<OrdenServicioResponse>>> listarPorEstado(
            @PathVariable EstadoOrden estado) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Órdenes de servicio por estado obtenidas correctamente.",
                        ordenServicioService.listarPorEstado(estado)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrdenServicioResponse>> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody OrdenServicioRequest ordenServicioRequest) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Orden de servicio actualizada correctamente.",
                        ordenServicioService.actualizar(id, ordenServicioRequest)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<ApiResponse<OrdenServicioResponse>> cambiarEstado(
            @PathVariable Long id,
            @Valid @RequestBody CambioEstadoOrdenRequest estadoOrdenRequest) {

        return ResponseEntity.ok(
                ApiResponse.ok(
                        "Estado de la orden de servicio actualizado correctamente.",
                        ordenServicioService.cambiarEstado(id, estadoOrdenRequest)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable Long id) {
		ordenServicioService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}