package com.motorbit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorbit.model.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    boolean existsByPlaca(String placa);
    List<Vehiculo> findByCliente_Id(Long clienteId);
}