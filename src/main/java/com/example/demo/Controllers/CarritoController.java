package com.example.demo.Controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Models.CarritoCompra;
import com.example.demo.Repositories.CarritoCompraRepository;

import javax.servlet.http.HttpSession;

@Controller
public class CarritoController {

    @Autowired
    private CarritoCompraRepository carritoCompraRepository;
    
    // Verificar autenticación
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("usuarioId") != null;
    }
    
    @GetMapping("/carrito")
    public String verCarrito(HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        
        // Obtener items del carrito
        List<CarritoCompra> itemsCarrito = carritoCompraRepository.findByUsuarioIdAndEstado(
                usuarioId, CarritoCompra.EstadoCarrito.EN_PROCESO);
        
        // Calcular total
        BigDecimal total = itemsCarrito.stream()
            .map(CarritoCompra::getPrecioCompra)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        model.addAttribute("itemsCarrito", itemsCarrito);
        model.addAttribute("totalCarrito", total);
        
        return "carrito";
    }
    
    @PostMapping("/eliminar-carrito")
    public String eliminarDelCarrito(
            @RequestParam Integer carritoId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        
        try {
            CarritoCompra carrito = carritoCompraRepository.findById(carritoId)
                    .orElseThrow(() -> new RuntimeException("Item no encontrado"));
            
            // Verificar que el item pertenece al usuario
            if (!carrito.getUsuario().getId().equals(usuarioId)) {
                throw new RuntimeException("No tienes permiso para eliminar este item");
            }
            
            carritoCompraRepository.delete(carrito);
            redirectAttributes.addFlashAttribute("mensaje", "Item eliminado del carrito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        }
        
        return "redirect:/carrito";
    }
    
    @PostMapping("/procesar-compra")
    public String procesarCompra(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        
        try {
            List<CarritoCompra> items = carritoCompraRepository.findByUsuarioIdAndEstado(
                    usuarioId, CarritoCompra.EstadoCarrito.EN_PROCESO);
            
            if (items.isEmpty()) {
                throw new RuntimeException("No hay items en el carrito");
            }
            
            LocalDateTime ahora = LocalDateTime.now();
            
            for (CarritoCompra item : items) {
                item.setEstado(CarritoCompra.EstadoCarrito.COMPRADO);
                item.setFechaCompra(ahora);
                
                // Calcular fecha de expiración
                LocalDateTime fechaExpiracion;
                switch (item.getTipoPlan()) {
                    case MENSUAL: fechaExpiracion = ahora.plusMonths(1); break;
                    case TRIMESTRAL: fechaExpiracion = ahora.plusMonths(3); break;
                    case ANUAL: fechaExpiracion = ahora.plusYears(1); break;
                    default: fechaExpiracion = ahora.plusMonths(1);
                }
                
                item.setFechaExpiracion(fechaExpiracion);
                carritoCompraRepository.save(item);
            }
            
            redirectAttributes.addFlashAttribute("mensaje", 
                    "¡Compra realizada con éxito! Puedes ver tus suscripciones en 'Mis Suscripciones'");
            return "redirect:/mis-suscripciones";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/carrito";
        }
    }
}