package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class MisComprasController {

    @Autowired
    private CarritoCompraRepository carritoCompraRepository;
    
    // Verificar autenticación
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("usuarioId") != null;
    }
    
    @GetMapping("/mis-suscripciones")
    public String misSuscripciones(HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        
        // Obtener suscripciones activas (compradas)
        List<CarritoCompra> suscripciones = carritoCompraRepository.findByUsuarioIdAndEstado(
                usuarioId, CarritoCompra.EstadoCarrito.COMPRADO);
        
        model.addAttribute("suscripciones", suscripciones);
        
        return "mis-suscripciones";
    }
    
    @GetMapping("/acceder-servicio/{id}")
    public String accederServicio(
            @PathVariable Integer id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        
        // Verificar que el usuario tiene una suscripción activa para este servicio
        boolean tieneAcceso = carritoCompraRepository
                .findByUsuarioIdAndServicioIdAndEstado(
                    usuarioId, id, CarritoCompra.EstadoCarrito.COMPRADO)
                .isPresent();
        
        if (!tieneAcceso) {
            redirectAttributes.addFlashAttribute("error", 
                    "No tienes una suscripción activa para este servicio");
            return "redirect:/mis-suscripciones";
        }
        
        // Aquí implementarías la lógica para acceder al servicio
        redirectAttributes.addFlashAttribute("mensaje", 
                "Accediendo al servicio. Esta funcionalidad está en desarrollo.");
        return "redirect:/mis-suscripciones";
    }
}