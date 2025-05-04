package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.CarritoCompra;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoCompraRepository extends JpaRepository<CarritoCompra, Integer> {
    
    // Encontrar items en el carrito de un usuario
    List<CarritoCompra> findByUsuarioIdAndEstado(Integer usuarioId, CarritoCompra.EstadoCarrito estado);
    
    // Verificar si un servicio ya está en el carrito
    Optional<CarritoCompra> findByUsuarioIdAndServicioIdAndEstado(
        Integer usuarioId, Integer servicioId, CarritoCompra.EstadoCarrito estado);
    
    // Contar items en el carrito
    Long countByUsuarioIdAndEstado(Integer usuarioId, CarritoCompra.EstadoCarrito estado);
}