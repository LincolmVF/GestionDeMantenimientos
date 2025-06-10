package com.prototipo.prototipoapp.controller.maquina;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prototipo.prototipoapp.entity.maquina.MantenimientoEntity;
import com.prototipo.prototipoapp.entity.maquina.MaquinaEntity;
import com.prototipo.prototipoapp.repository.maquina.MantenimientoRepository;
import com.prototipo.prototipoapp.repository.maquina.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mantenimientos")
public class MantenimientoController {
    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private MaquinaRepository maquinaRepository;

    @Autowired
    public MantenimientoController(MantenimientoRepository mantenimientoRepository) {
        this.mantenimientoRepository = mantenimientoRepository;
    }
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/nueva/{maquinaId}")
    @PreAuthorize("hasRole('Admin') ")
    public String mostrarFormularioDeNuevoMantenimiento(@PathVariable Long maquinaId, Model model) {
        MantenimientoEntity mantenimiento = new MantenimientoEntity();
        MaquinaEntity maquina = maquinaRepository.findById(maquinaId).orElse(null);
        if (maquina == null) {
            // Manejar el caso en que la máquina no existe
            return "error_page"; // O redirigir a alguna página de error
        }
        mantenimiento.setMaquina(maquina);
        model.addAttribute("mantenimiento", mantenimiento);
        return "mantenimiento_form";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('Admin') ")
    public String guardarMantenimiento(MantenimientoEntity mantenimiento) {
        if (mantenimiento.getMaquina() == null) {
            // Manejar el error si no se ha asignado la maquina
            return "error_page2"; // O redirigir a una página de error
        }
        mantenimientoRepository.save(mantenimiento);
        return "redirect:/mantenimientos/lista/" + mantenimiento.getMaquina().getIdMaquina();
    }

    @GetMapping("/lista/{maquinaId}")
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String listarMantenimientos(@PathVariable Long maquinaId, Model model) {
        // Buscar la máquina en la base de datos
        MaquinaEntity maquina = maquinaRepository.findById(maquinaId).orElse(null);

        // Si la máquina no existe, redirige a una página de error o lista general de máquinas
        if (maquina == null) {
            return "error_page"; // O puedes redirigir a otra vista, como una lista general de máquinas
        }

        // Obtener la lista de mantenimientos asociados a la máquina
        List<MantenimientoEntity> mantenimientos = mantenimientoRepository.findByMaquina(maquina);

        // Si no hay mantenimientos, puedes agregar un mensaje o tomar alguna acción
        if (mantenimientos.isEmpty()) {
            model.addAttribute("mensaje", "No hay mantenimientos registrados para esta máquina.");
        }

        // Agregar atributos al modelo
        model.addAttribute("mantenimientos", mantenimientos);
        model.addAttribute("maquina", maquina);

        // Retornar la vista para listar los mantenimientos
        return "mantenimiento_list";
    }

    @GetMapping("/volver")
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String volverAMaquina() {
        return "redirect:/maquinas";
    }


    @GetMapping("/dashboard/{maquinaId}")
    public String mostrarDashboard(@PathVariable Long maquinaId, Model model) throws JsonProcessingException {
        MaquinaEntity maquina = maquinaRepository.findById(maquinaId).orElse(null);
        if (maquina == null) {
            return "error_page";
        }

        List<MantenimientoEntity> mantenimientos = mantenimientoRepository.findByMaquina(maquina);

// Calcular la fecha del último mantenimiento
        LocalDate ultimaFechaMantenimiento = mantenimientos.stream()
                .map(MantenimientoEntity::getFechaMantenimiento)
                .max(LocalDate::compareTo)
                .orElse(null);


        // Calcular costos totales por tipo de mantenimiento
        double costoTotalCorrectivo = mantenimientos.stream()
                .filter(m -> "Correctivo".equals(m.getTipoMantenimiento()))
                .mapToDouble(MantenimientoEntity::getCosto)
                .sum();

        double costoTotalPreventivo = mantenimientos.stream()
                .filter(m -> "Preventivo".equals(m.getTipoMantenimiento()))
                .mapToDouble(MantenimientoEntity::getCosto)
                .sum();

        // Calcular conteos por tipo de mantenimiento
        long conteoCorrectivo = mantenimientos.stream()
                .filter(m -> "Correctivo".equals(m.getTipoMantenimiento()))
                .count();

        long conteoPreventivo = mantenimientos.stream()
                .filter(m -> "Preventivo".equals(m.getTipoMantenimiento()))
                .count();

        // Agregar atributos al modelo
        model.addAttribute("ultimaFechaMantenimiento", ultimaFechaMantenimiento);
        model.addAttribute("maquina", maquina);
        model.addAttribute("mantenimientos", mantenimientos);
        model.addAttribute("costoTotalCorrectivo", costoTotalCorrectivo);
        model.addAttribute("costoTotalPreventivo", costoTotalPreventivo);
        model.addAttribute("conteoCorrectivo", conteoCorrectivo);
        model.addAttribute("conteoPreventivo", conteoPreventivo);

        return "mantenimiento_dashboard";
    }






    @GetMapping("/editar/{id}")
    @PreAuthorize("hasRole('Admin') ")
    public String mostrarFormularioDeEditarMantenimiento(@PathVariable Long id, Model model) {
        Optional<MantenimientoEntity> mantenimientoOpt = mantenimientoRepository.findById(id);
        if (mantenimientoOpt.isEmpty()) {
            return "error_page";
        }
        model.addAttribute("mantenimiento", mantenimientoOpt.get());
        return "mantenimiento_form";
    }

    @PostMapping("/actualizar")
    @PreAuthorize("hasRole('Admin') ")
    public String guardarOActualizarMantenimiento(@ModelAttribute MantenimientoEntity mantenimiento) {
        // Recuperar la máquina si es una actualización
        if (mantenimiento.getIdMantenimiento() != null) {
            Optional<MantenimientoEntity> mantenimientoExistente = mantenimientoRepository.findById(mantenimiento.getIdMantenimiento());
            if (mantenimientoExistente.isPresent()) {
                mantenimiento.setMaquina(mantenimientoExistente.get().getMaquina());
            } else {
                return "error_page"; // Si el mantenimiento no existe, mostrar error
            }
        }
        mantenimientoRepository.save(mantenimiento);
        return "redirect:/mantenimientos/lista/" + mantenimiento.getMaquina().getIdMaquina();
    }



    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('Admin') ")
    public String eliminarMantenimiento(@PathVariable Long id) {
        MantenimientoEntity mantenimiento = mantenimientoRepository.findById(id).orElse(null);
        if (mantenimiento == null) {
            return "error_page";
        }
        mantenimientoRepository.deleteById(id);
        return "redirect:/mantenimientos/lista/" + mantenimiento.getMaquina().getIdMaquina();
    }




}