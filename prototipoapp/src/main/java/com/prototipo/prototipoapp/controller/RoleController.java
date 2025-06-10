package com.prototipo.prototipoapp.controller;

import com.prototipo.prototipoapp.entity.RoleEntity;
import com.prototipo.prototipoapp.repository.PermissionRepository;
import com.prototipo.prototipoapp.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;

@Controller
@RequestMapping("/rol")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    // Mostrar el formulario para crear un nuevo rol
    @GetMapping("/crear")
    @PreAuthorize("hasRole('Admin')")
    public String mostrarFormularioCrear(Model model) {
        RoleEntity rol = new RoleEntity();
        if (rol.getPermisionList() == null) {
            rol.setPermisionList(new HashSet<>());
        }
        model.addAttribute("rol", rol);  // Crea un nuevo objeto RoleEntity para el formulario
        model.addAttribute("permisos", permissionRepository.findAll());  // Cargar todos los permisos
        return "rol_crear"; // Vista para crear rol
    }


    // Guardar un nuevo rol (POST para crear)
    @PostMapping("/guardar")
    @PreAuthorize("hasRole('Admin')")
    public String guardarRol(RoleEntity rol) {
        // Asegurarse de que la lista de permisos no esté vacía
        if (rol.getPermisionList() == null) {
            rol.setPermisionList(new HashSet<>());
        }
        roleRepository.save(rol);  // Guarda el nuevo rol con los permisos seleccionados
        return "redirect:/rol";  // Redirige a la lista de roles
    }


    // Mostrar la lista de roles
    @GetMapping
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String mostrarListaRoles(Model model) {
        model.addAttribute("roles", roleRepository.findAll());  // Recupera todos los roles desde la base de datos
        return "rol_list"; // Vista para mostrar la lista de roles
    }

    // Mostrar el formulario para editar un rol
    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('Admin')")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        RoleEntity rol = roleRepository.findById(id).orElse(null);  // Busca el rol por id
        if (rol != null) {
            model.addAttribute("rol", rol);  // Carga el rol en el formulario para editar
            model.addAttribute("permisos", permissionRepository.findAll());  // Cargar todos los permisos
            return "rol_crear";  // Vista para editar rol
        } else {
            model.addAttribute("error", "Rol no encontrado");
            return "rol_list";  // Si no se encuentra el rol, redirige a la lista
        }
    }

    // Guardar los cambios del rol (POST para editar)
    @PostMapping("/editar/guardar")
    @PreAuthorize("hasRole('Admin')")
    public String guardarRolEditar(@ModelAttribute RoleEntity rol) {
        RoleEntity rolExistente = roleRepository.findById(rol.getId()).orElse(null);

        if (rolExistente != null) {
            rolExistente.setNombreRol(rol.getNombreRol());
            rolExistente.setPermisionList(rol.getPermisionList());
            roleRepository.save(rolExistente); // Se actualiza el rol en lugar de crear uno nuevo
        } else {
            roleRepository.save(rol); // Si no existe, lo creamos
        }

        return "redirect:/rol";
    }


    // Eliminar un rol
    @GetMapping("/borrar/{id}")
    @PreAuthorize("hasRole('Admin')")
    public String borrarRol(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            RoleEntity rol = roleRepository.findById(id).orElse(null);

            if (rol != null) {
                rol.getPermisionList().clear(); // Elimina la relación con los permisos
                roleRepository.save(rol); // Guarda el cambio para eliminar la relación
                roleRepository.deleteById(id); // Ahora sí, elimina el rol
                redirectAttributes.addFlashAttribute("mensaje", "Rol borrado exitosamente");
            } else {
                redirectAttributes.addFlashAttribute("mensaje", "Rol no encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al borrar el rol: " + e.getMessage());
        }
        return "redirect:/rol";
    }

}
