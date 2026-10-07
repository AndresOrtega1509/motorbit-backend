package com.motorbit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorbit.model.OrdenServicio;
import com.motorbit.model.enums.EstadoOrden;

public interface OrdenServicioRepository extends JpaRepository<OrdenServicio, Long> {
    List<OrdenServicio> findByVehiculo_Id(Long vehiculoId);
    List<OrdenServicio> findByEstado(EstadoOrden estadoOrden);
}