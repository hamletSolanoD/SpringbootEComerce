package com.example.demo.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "carrito_compras", uniqueConstraints = {
    @UniqueConstraint(name = "unique_usuario_servicio_activo", 
                     columnNames = {"usuario_id", "servicio_id", "estado"})
})
public class CarritoCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCarrito estado = EstadoCarrito.EN_PROCESO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_plan", nullable = false)
    private TipoPlan tipoPlan;
    
    @Column(name = "fecha_agregado")
    private LocalDateTime fechaAgregado = LocalDateTime.now();
    
    @Column(name = "fecha_compra")
    private LocalDateTime fechaCompra;
    
    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;
    
    @Column(name = "precio_compra", precision = 10, scale = 2)
    private BigDecimal precioCompra;
    
    public enum EstadoCarrito {
        EN_PROCESO, COMPRADO, EXPIRADO
    }
    
    public enum TipoPlan {
        MENSUAL, TRIMESTRAL, ANUAL
    }
    
    // Getters
    public Integer getId() {
        return id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public Servicio getServicio() {
        return servicio;
    }
    
    public EstadoCarrito getEstado() {
        return estado;
    }
    
    public TipoPlan getTipoPlan() {
        return tipoPlan;
    }
    
    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }
    
    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }
    
    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }
    
    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }
    
    // Setters
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    
    public void setEstado(EstadoCarrito estado) {
        this.estado = estado;
    }
    
    public void setTipoPlan(TipoPlan tipoPlan) {
        this.tipoPlan = tipoPlan;
    }
    
    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
    
    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
    
    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
    
    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }
}