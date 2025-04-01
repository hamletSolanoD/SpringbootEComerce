package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ComingSoonController {

    // Verificar autenticación
    private boolean isAuthenticated(HttpSession session) {
        return session.getAttribute("usuarioId") != null;
    }
    
    @GetMapping({"/reportes", "/configuracion", "/soporte"})
    public String comingSoon(HttpSession session, Model model) {
        if (!isAuthenticated(session)) {
            return "redirect:/";
        }
        
        // Obtener la URL actual
        String requestURI = (String) session.getAttribute("javax.servlet.forward.request_uri");
        String section = "";
        
        if (requestURI != null) {
            section = requestURI.substring(requestURI.lastIndexOf('/') + 1);
        }
        
        String activeMenu;
        String sectionTitle;
        String sectionDescription;
        String sectionIcon;
        
        switch (section) {
            case "reportes":
                activeMenu = "reportes";
                sectionTitle = "Reportes";
                sectionDescription = "Visualiza estadísticas y análisis detallados de tus servicios de web scraping.";
                sectionIcon = "📊";
                break;
            case "configuracion":
                activeMenu = "configuracion";
                sectionTitle = "Configuración";
                sectionDescription = "Personaliza tu cuenta y ajusta las preferencias de la plataforma.";
                sectionIcon = "⚙️";
                break;
            case "soporte":
                activeMenu = "soporte";
                sectionTitle = "Soporte";
                sectionDescription = "Obtén ayuda y asistencia para resolver cualquier problema con nuestros servicios.";
                sectionIcon = "📞";
                break;
            default:
                activeMenu = "";
                sectionTitle = "Próximamente";
                sectionDescription = "Esta sección estará disponible pronto.";
                sectionIcon = "🚀";
        }
        
        model.addAttribute("activeMenu", activeMenu);
        model.addAttribute("sectionTitle", sectionTitle);
        model.addAttribute("sectionDescription", sectionDescription);
        model.addAttribute("sectionIcon", sectionIcon);
        
        return "coming-soon";
    }
 
}