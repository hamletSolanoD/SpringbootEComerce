// Servicio.java
package com.example.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "servicios")
public class Servicio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "precio_por_mes", precision = 10, scale = 2)
    private BigDecimal precioPorMes;
    
    @Column(name = "precio_por_anio", precision = 10, scale = 2)
    private BigDecimal precioPorAnio;
    
    @Column(name = "precio_por_trimestre", precision = 10, scale = 2)
    private BigDecimal precioPorTrimestre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Column(length = 50)
    private String categoria;
    
    @Column(name = "fecha_disponibilidad_inicio")
    private LocalDate fechaDisponibilidadInicio;
    
    @Column(name = "fecha_disponibilidad_fin")
    private LocalDate fechaDisponibilidadFin;
    
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();
}