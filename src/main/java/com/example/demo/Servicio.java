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

    // Servicio.java (añadir estos métodos a tu clase existente)
// Getters
public Integer getId() {
    return id;
}

public String getNombre() {
    return nombre;
}

public BigDecimal getPrecioPorMes() {
    return precioPorMes;
}

public BigDecimal getPrecioPorAnio() {
    return precioPorAnio;
}

public BigDecimal getPrecioPorTrimestre() {
    return precioPorTrimestre;
}

public String getDescripcion() {
    return descripcion;
}

public String getCategoria() {
    return categoria;
}

public LocalDate getFechaDisponibilidadInicio() {
    return fechaDisponibilidadInicio;
}

public LocalDate getFechaDisponibilidadFin() {
    return fechaDisponibilidadFin;
}

public Boolean getActivo() {
    return activo;
}

public LocalDateTime getFechaCreacion() {
    return fechaCreacion;
}

public LocalDateTime getFechaActualizacion() {
    return fechaActualizacion;
}

// Setters
public void setId(Integer id) {
    this.id = id;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public void setPrecioPorMes(BigDecimal precioPorMes) {
    this.precioPorMes = precioPorMes;
}

public void setPrecioPorAnio(BigDecimal precioPorAnio) {
    this.precioPorAnio = precioPorAnio;
}

public void setPrecioPorTrimestre(BigDecimal precioPorTrimestre) {
    this.precioPorTrimestre = precioPorTrimestre;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

public void setCategoria(String categoria) {
    this.categoria = categoria;
}

public void setFechaDisponibilidadInicio(LocalDate fechaDisponibilidadInicio) {
    this.fechaDisponibilidadInicio = fechaDisponibilidadInicio;
}

public void setFechaDisponibilidadFin(LocalDate fechaDisponibilidadFin) {
    this.fechaDisponibilidadFin = fechaDisponibilidadFin;
}

public void setActivo(Boolean activo) {
    this.activo = activo;
}

public void setFechaCreacion(LocalDateTime fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
}

public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
}
}