package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {
    List<Servicio> findByActivoTrue();
    List<Servicio> findByCategoria(String categoria);
}