package com.prototipo.prototipoapp.controller;

import com.prototipo.prototipoapp.entity.PermisionEntity;
import com.prototipo.prototipoapp.repository.PermissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/permiso")
public class PermissionController {

    @Autowired
    private PermissionRepository permisionRepository;

    // Mostrar el formulario para crear un nuevo permiso
    @GetMapping("/crear")
    @PreAuthorize("hasRole('Admin')")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("permiso", new PermisionEntity());
        return "permiso_crear"; // Vista de crear
    }

    // Guardar un nuevo permiso (POST para crear)
    @PostMapping("/guardar")
    @PreAuthorize("hasRole('Admin')")
    public String guardarPermiso(PermisionEntity permiso) {
        permisionRepository.save(permiso); // Guarda el nuevo permiso
        return "redirect:/permiso"; // Redirige a la lista de permisos
    }

    // Mostrar la lista de permisos
    @GetMapping
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String mostrarListaPermisos(Model model) {
        model.addAttribute("permisos", permisionRepository.findAll());
        return "permiso_list"; // Vista de la lista de permisos
    }

    // Mostrar el formulario para editar un permiso
    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('Admin')")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        PermisionEntity permiso = permisionRepository.findById(id).orElse(null);
        if (permiso != null) {
            model.addAttribute("permiso", permiso); // Carga el permiso para editar
            return "permiso_crear"; // Vista de editar
        } else {
            model.addAttribute("error", "Permiso no encontrado");
            return "permiso_list"; // Si no lo encuentra, vuelve a la lista
        }
    }

    // Guardar los cambios del permiso (POST para editar)
    @PostMapping("editar/guardar")
    @PreAuthorize("hasRole('Admin')")
    public String guardarPermisoEditar(PermisionEntity permiso) {
        permisionRepository.save(permiso); // Guarda la actualización del permiso
        return "redirect:/permiso"; // Redirige a la lista de permisos
    }

    // Eliminar un permiso
    @GetMapping("/borrar/{id}")
    @PreAuthorize("hasRole('Admin')")
    public String borrarPermiso(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            permisionRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Permiso borrado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al borrar el permiso");
        }
        return "redirect:/permiso"; // Redirige a la lista de permisos
    }
}
