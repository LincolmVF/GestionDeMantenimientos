package com.prototipo.prototipoapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

@GetMapping("/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public String adminDashboard() {
    return "adminPage";
}
}
