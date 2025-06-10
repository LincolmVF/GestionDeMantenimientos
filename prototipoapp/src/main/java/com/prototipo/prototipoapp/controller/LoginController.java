package com.prototipo.prototipoapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Retorna el nombre del archivo de la vista (login.html)
    }

    @GetMapping("/home")
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String home() {
        return "home";  // Página que se muestra después de iniciar sesión
    }
    @GetMapping("/homeAdmin")
    @PreAuthorize("hasRole('Admin')")
    public String homeAdmin() {
        return "homeAdmin";  // Página que se muestra después de iniciar sesión
    }
}
