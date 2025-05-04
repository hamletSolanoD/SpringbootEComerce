// DashboardController.java
package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Models.CarritoCompra;
import com.example.demo.Models.Servicio;
import com.example.demo.Models.Usuario;
import com.example.demo.Models.CarritoCompra.EstadoCarrito;
import com.example.demo.Models.CarritoCompra.TipoPlan;
import com.example.demo.Repositories.CarritoCompraRepository;
import com.example.demo.Repositories.ServicioRepository;
import com.example.demo.Repositories.UsuarioRepository;

import javax.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private ServicioRepository servicioRepository;


    @Autowired
private CarritoCompraRepository carritoCompraRepository;

@Autowired
private UsuarioRepository usuarioRepository;

@PostMapping("/agregar-carrito")
public String agregarAlCarrito(
        @RequestParam Integer servicioId,
        @RequestParam String tipoPlan,
        HttpSession session,
        RedirectAttributes redirectAttributes) {
    
    if (!isAuthenticated(session)) {
        return "redirect:/";
    }
    
    Integer usuarioId = (Integer) session.getAttribute("usuarioId");
    
    try {
        // Verificar si ya existe en el carrito
        var carritoExistente = carritoCompraRepository
                .findByUsuarioIdAndServicioIdAndEstado(
                    usuarioId, servicioId, CarritoCompra.EstadoCarrito.EN_PROCESO);
        
        // Obtener usuario y servicio
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        
        CarritoCompra carrito;
        CarritoCompra.TipoPlan plan = CarritoCompra.TipoPlan.valueOf(tipoPlan);
        
        if (carritoExistente.isPresent()) {
            // Actualizar el plan si ya existe
            carrito = carritoExistente.get();
            carrito.setTipoPlan(plan);
        } else {
            // Crear nuevo item
            carrito = new CarritoCompra();
            carrito.setUsuario(usuario);
            carrito.setServicio(servicio);
            carrito.setTipoPlan(plan);
        }
        
        // Establecer precio según el plan
        switch (plan) {
            case MENSUAL: carrito.setPrecioCompra(servicio.getPrecioPorMes()); break;
            case TRIMESTRAL: carrito.setPrecioCompra(servicio.getPrecioPorTrimestre()); break;
            case ANUAL: carrito.setPrecioCompra(servicio.getPrecioPorAnio()); break;
        }
        
        carritoCompraRepository.save(carrito);
        
        redirectAttributes.addFlashAttribute("mensaje", 
                "Servicio '" + servicio.getNombre() + "' agregado al carrito con plan " + tipoPlan);
        
        return "redirect:/carrito";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        return "redirect:/dashboard";
    }
}

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
}