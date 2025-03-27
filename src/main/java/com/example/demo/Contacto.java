// Contacto.java
package com.example.demo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contactos")
public class Contacto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 150)
    private String email;
    
    @Column(nullable = false, length = 200)
    private String asunto;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    
    @Enumerated(EnumType.STRING)
    private EstadoContacto estado = EstadoContacto.PENDIENTE;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion = LocalDateTime.now();
    
    @Column(name = "ip_remitente", length = 45)
    private String ipRemitente;
    
    @Column(columnDefinition = "TEXT")
    private String respuesta;
    
    @Column(name = "fecha_respuesta")
    private LocalDateTime fechaRespuesta;
    
    public enum EstadoContacto {
        PENDIENTE, LEIDO, RESPONDIDO, ARCHIVADO
    }
}