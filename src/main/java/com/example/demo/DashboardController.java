// DashboardController.java
package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private ServicioRepository servicioRepository;

    // Verificar autenticación para todas las rutas del dashboard
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("usuarioId") != null;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        // Verificar si el usuario está autenticado
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        // Obtener servicios activos
        List<Servicio> servicios = servicioRepository.findByActivoTrue();
        model.addAttribute("servicios", servicios);
        
        // Obtener categorías únicas para el filtro
        List<String> categorias = servicios.stream()
                .map(Servicio::getCategoria)
                .distinct()
                .toList();
        model.addAttribute("categorias", categorias);
        
        return "dashboard";
    }
    
    @GetMapping("/dashboard/categoria/{categoria}")
    public String serviciosPorCategoria(
            @PathVariable String categoria,
            HttpSession session, 
            Model model) {
        
        // Verificar si el usuario está autenticado
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        // Obtener servicios por categoría
        List<Servicio> servicios = servicioRepository.findByCategoria(categoria);
        model.addAttribute("servicios", servicios);
        model.addAttribute("categoriaActual", categoria);
        
        // Obtener todas las categorías para el filtro
        List<String> categorias = servicioRepository.findByActivoTrue().stream()
                .map(Servicio::getCategoria)
                .distinct()
                .toList();
        model.addAttribute("categorias", categorias);
        
        return "dashboard";
    }
    
    @GetMapping("/servicio/{id}")
    public String detalleServicio(
            @PathVariable Integer id,
            HttpSession session, 
            Model model) {
        
        // Verificar si el usuario está autenticado
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        // Obtener detalles del servicio
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        
        model.addAttribute("servicio", servicio);
        
        return "detalle-servicio";
    }
    
    @PostMapping("/agregar-carrito")
    public String agregarAlCarrito(
            @RequestParam Integer servicioId,
            @RequestParam String tipoPlan,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        // Verificar si el usuario está autenticado
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        // Aquí iría la lógica para agregar al carrito
        // Por ahora solo mostramos un mensaje de éxito
        redirectAttributes.addFlashAttribute("mensaje", 
                "Servicio agregado al carrito con plan " + tipoPlan);
        
        return "redirect:/dashboard";
    }
}