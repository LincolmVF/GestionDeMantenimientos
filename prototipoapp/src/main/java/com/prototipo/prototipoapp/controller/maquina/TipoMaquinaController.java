package com.prototipo.prototipoapp.controller.maquina;

import com.prototipo.prototipoapp.entity.maquina.TipoMaquina;
import com.prototipo.prototipoapp.service.maquina.TipoMaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("tipoMaquina")
public class TipoMaquinaController {

    @Autowired
    private TipoMaquinaService tipoMaquinaService;

    @GetMapping("/nuevoTipoMaquina")
    @PreAuthorize("hasRole('Admin')")
    public String nuevoTipoMaquina(Model model) {
        List<TipoMaquina> tipos;
        tipos=tipoMaquinaService.listarTipoMaquinas();


        model.addAttribute("tipoMaquina", tipos);
        return "tipoDeMaquina";
    }



    @PostMapping("/agregar-tipo")
    @PreAuthorize("hasRole('Admin')")
    public String agregarTipo(@RequestParam String nombre, RedirectAttributes redirectAttributes) {
        boolean agregado = tipoMaquinaService.agregarTipoMaquina(nombre);

        if (!agregado) {
            redirectAttributes.addFlashAttribute("error", "Ese tipo de máquina ya existe.");
        } else {
            redirectAttributes.addFlashAttribute("success", "Tipo de máquina agregado correctamente.");
        }

        return "redirect:/maquinas";  // Redirige al formulario de nueva máquina
    }


    @PostMapping("/actualizarTipo")
    @PreAuthorize("hasRole('Admin')")
    public String actualizarTipo(@RequestParam Long idTipoMaquina, @RequestParam String nombre, RedirectAttributes redirectAttributes) {
        boolean actualizado = tipoMaquinaService.actualizarTipoMaquina(idTipoMaquina, nombre);

        if (!actualizado) {
            redirectAttributes.addFlashAttribute("error", "No se pudo actualizar el tipo de máquina.");
        } else {
            redirectAttributes.addFlashAttribute("success", "Tipo de máquina actualizado correctamente.");
        }

        return "redirect:/tipoMaquina/nuevoTipoMaquina"; // Redirige al listado de tipos
    }

    @PostMapping("/borrarTipo")
    @PreAuthorize("hasRole('Admin')")
    public String borrarTipo(@RequestParam Long idTipoMaquina, RedirectAttributes redirectAttributes) {
        boolean borrado = tipoMaquinaService.eliminarTipoMaquina(idTipoMaquina);

        if (!borrado) {
            redirectAttributes.addFlashAttribute("error", "No se pudo borrar el tipo de máquina.");
        } else {
            redirectAttributes.addFlashAttribute("success", "Tipo de máquina borrado correctamente.");
        }

        return "redirect:/tipoMaquina/nuevoTipoMaquina"; // Redirige al listado de tipos
    }



}
