// AuthController.java
package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    // Encoder para contraseñas directamente en el controlador
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "index";
    }
    
    @PostMapping("/registro")
    public String registro(@Valid @ModelAttribute("usuario") Usuario usuario, 
                          BindingResult result, 
                          RedirectAttributes redirectAttributes,
                          HttpSession session) {
        
        if (result.hasErrors()) {
            return "index";
        }
        
        try {
            // Verificar si el email ya existe
            if (usuarioRepository.existsByEmail(usuario.getEmail())) {
                redirectAttributes.addFlashAttribute("errorRegistro", "El email ya está registrado");
                return "redirect:/";
            }
            
            // Encriptar contraseña
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuario.setTipo(Usuario.TipoUsuario.COMPRADOR);
            
            // Guardar usuario
            Usuario usuarioRegistrado = usuarioRepository.save(usuario);
            
            // Establecer sesión
            session.setAttribute("usuarioId", usuarioRegistrado.getId());
            session.setAttribute("usuarioNombre", usuarioRegistrado.getNombre());
            session.setAttribute("usuarioEmail", usuarioRegistrado.getEmail());
            session.setAttribute("usuarioTipo", usuarioRegistrado.getTipo().toString());
            
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorRegistro", "Error al registrar: " + e.getMessage());
            return "redirect:/";
        }
    }
    
    @PostMapping("/iniciosesion")
    public String inicioSesion(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              RedirectAttributes redirectAttributes,
                              HttpSession session) {
        
        try {
            // Buscar usuario por email
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Verificar contraseña
            if (!passwordEncoder.matches(password, usuario.getPassword())) {
                throw new RuntimeException("Contraseña incorrecta");
            }
            
            // Establecer sesión
            session.setAttribute("usuarioId", usuario.getId());
            session.setAttribute("usuarioNombre", usuario.getNombre());
            session.setAttribute("usuarioEmail", usuario.getEmail());
            session.setAttribute("usuarioTipo", usuario.getTipo().toString());
            
            return "redirect:/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorLogin", e.getMessage());
            return "redirect:/";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    

}