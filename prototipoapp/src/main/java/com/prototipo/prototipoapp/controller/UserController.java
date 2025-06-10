package com.prototipo.prototipoapp.controller;

import com.prototipo.prototipoapp.entity.RoleEntity;
import com.prototipo.prototipoapp.entity.UserEntity;
import com.prototipo.prototipoapp.service.RoleService;
import com.prototipo.prototipoapp.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @GetMapping("/")
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String homePage() {
        return "home";  // El nombre de la plantilla Thymeleaf que deseas mostrar
    }


    // Ruta para mostrar el formulario de crear usuario
    @GetMapping("/usuario/crear")

    public String crearUsuarioForm(Model model) {
        List<RoleEntity> roles = roleService.listarRoles();  // Obtiene todos los roles
        boolean yaExisteAdmin = userService.existsAdmin();

        model.addAttribute("yaExisteAdmin", yaExisteAdmin);
        model.addAttribute("roles", roles);  // Agrega los roles al modelo para Thymeleaf
        model.addAttribute("user", new UserEntity());  // Crea un nuevo objeto UserEntity
        return "crear_usuario";  // Retorna el nombre de la plantilla Thymeleaf
    }

    // Ruta para guardar el usuario
    @PostMapping("/usuario/guardar")

    public String guardarUsuario(@RequestParam("roleId") Long roleId, UserEntity user) {
        System.out.println("Entra en el método guardarUsuario");  // Esto debería aparecer en la consola
        // Verifica que los parámetros recibidos sean correctos
        System.out.println("Role ID: " + roleId);
        System.out.println("Usuario: " + user);
        RoleEntity selectedRole = roleService.buscarRolePorId(roleId);

        // Verificamos si se ha recuperado el rol correctamente
        if (selectedRole == null) {
            System.out.println("Error: El rol no fue encontrado. ID proporcionado: " + roleId);
            return "error";  // Mostrar página de error si el rol no fue encontrado
        }


        // 🚨 Bloquear la creación de más usuarios con rol ADMIN si ya existe uno
        if ("Admin".equals(selectedRole.getNombreRol()) && userService.existsAdmin()) {
            System.out.println("Intento de crear otro ADMIN bloqueado.");
            return "redirect:/usuario/crear?error=Ya existe un administrador. No puedes crear otro.";
        }


        // Asignar el rol al usuario
        user.setRole(selectedRole);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);

        // Verificamos si los valores del usuario son correctos antes de guardar
        System.out.println("Creando usuario: " + user.getUsername() + " con rol: " + selectedRole.getNombreRol());

        // Guardar el usuario
        try {

                userService.guardadUsuario(user);

            System.out.println("Usuario creado correctamente: " + user.getUsername());
        } catch (Exception e) {
            System.out.println("Error al crear el usuario: " + e.getMessage());
            return "error";  // Mostrar página de error si algo salió mal
        }

        return "redirect:/";  // Redirige a la página principal o a la ruta que desees
    }
    // Ruta para mostrar la lista de usuarios
    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String mostrarListaUsuarios(Model model) {
        List<UserEntity> usuarios = userService.listarTodosUsuarios();  // Recupera todos los usuarios desde el servicio
        model.addAttribute("usuarios", usuarios);  // Agrega la lista de usuarios al modelo
        return "usuarios_lista";  // Vista para mostrar la lista de usuarios
    }


    // Ruta para eliminar un usuario
    @GetMapping("/usuario/eliminar/{id}")
    @PreAuthorize("hasRole('Admin')")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        try {
            userService.eliminarUsuario(id);
        } catch (Exception e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
            return "error";  // Mostrar página de error si algo salió mal
        }

        return "redirect:/usuarios";  // Redirige a la lista de usuarios
    }


    // Ruta para editar un usuario (mostrar el formulario con los datos existentes)
    @GetMapping("/usuario/editar/{id}")
    @PreAuthorize("hasRole('Admin')")
    public String editarUsuarioForm(@PathVariable("id") Long id, Model model) {
        UserEntity usuario = userService.buscarUsuarioPorid(id);
        if (usuario == null) {
            System.out.println("Usuario no encontrado con ID: " + id);
            return "error";  // Redirige a una página de error si el usuario no existe
        }

        List<RoleEntity> roles = roleService.listarRoles(); // Obtener todos los roles
        model.addAttribute("roles", roles);
        model.addAttribute("user", usuario);
        return "crear_usuario"; // Usa la misma plantilla para crear/editar
    }

    // Ruta para guardar cambios de un usuario editado
    @PostMapping("/usuario/editar/guardar")
    @PreAuthorize("hasRole('Admin')")
    public String actualizarUsuario(@RequestParam("roleId") Long roleId, @ModelAttribute UserEntity user) {
        System.out.println("Entra en el método actualizarUsuario");

        UserEntity usuarioExistente = userService.buscarUsuarioPorid(user.getId());
        if (usuarioExistente == null) {
            System.out.println("Error: Usuario no encontrado con ID: " + user.getId());
            return "error";
        }

        RoleEntity selectedRole = roleService.buscarRolePorId(roleId);
        if (selectedRole == null) {
            System.out.println("Error: Rol no encontrado con ID: " + roleId);
            return "error";
        }

        // Actualizar los datos del usuario
        usuarioExistente.setUsername(user.getUsername());
        usuarioExistente.setPassword(user.getPassword()); // Asegúrate de manejar el hashing si es necesario
        usuarioExistente.setRole(selectedRole);

        try {
            userService.guardadUsuario(usuarioExistente);
            System.out.println("Usuario actualizado correctamente: " + user.getUsername());
        } catch (Exception e) {
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
            return "error";
        }

        return "redirect:/usuarios";  // Redirige a la lista de usuarios
    }



}

