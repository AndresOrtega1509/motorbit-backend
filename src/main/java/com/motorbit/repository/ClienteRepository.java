package com.motorbit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.motorbit.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByCedula(String cedula);
}