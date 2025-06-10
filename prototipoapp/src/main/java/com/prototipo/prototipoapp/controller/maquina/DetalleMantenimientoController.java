package com.prototipo.prototipoapp.controller.maquina;


import com.prototipo.prototipoapp.entity.maquina.DetalleMantenimientoEntity;
import com.prototipo.prototipoapp.entity.maquina.MantenimientoEntity;
import com.prototipo.prototipoapp.entity.maquina.MaquinaEntity;
import com.prototipo.prototipoapp.repository.maquina.DetalleManRepository;
import com.prototipo.prototipoapp.repository.maquina.MantenimientoRepository;
import com.prototipo.prototipoapp.repository.maquina.MaquinaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/detalles")
public class DetalleMantenimientoController {
    @Autowired
    private DetalleManRepository detalleMantenimientoRepository;

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private MaquinaRepository maquinaRepository;


    private static final Logger logger = LoggerFactory.getLogger(DetalleMantenimientoController.class);



    // Mostrar el formulario para agregar un nuevo detalle o mostrar el detalle existente
    @GetMapping("/nuevo/{mantenimientoId}")
    @PreAuthorize("hasRole('Admin') ")
    public String mostrarFormularioDeNuevoDetalle(@PathVariable Long mantenimientoId, Model model) {
        // Busca el mantenimiento
        MantenimientoEntity mantenimiento = mantenimientoRepository.findById(mantenimientoId).orElse(null);

        if (mantenimiento == null) {
            // Manejar error si no se encuentra el mantenimiento
            return "error_page";  // Página de error si no se encuentra el mantenimiento
        }

        // Verificar si ya existe un detalle para este mantenimiento
        DetalleMantenimientoEntity detalleExistente = detalleMantenimientoRepository.findByMantenimiento(mantenimiento);

        if (detalleExistente != null) {
            // Si ya existe un detalle, redirige a la vista para mostrar los detalles
            return "redirect:/detalles/ver/" + mantenimientoId;  // Cambiar la ruta a /ver
        }

        // Si no existe, se crea un nuevo detalle
        DetalleMantenimientoEntity nuevoDetalle = new DetalleMantenimientoEntity();
        nuevoDetalle.setMantenimiento(mantenimiento);

        // Agregar el objeto detalle al modelo
        // Agregar la máquina asociada al mantenimiento al modelo
        MaquinaEntity maquina = mantenimiento.getMaquina();
        model.addAttribute("maquina", maquina);
        model.addAttribute("detalleMantenimiento", nuevoDetalle);
        return "detalle_form";  // Devuelve el formulario para agregar el detalle
    }

    // Guardar el detalle de mantenimiento
    @PostMapping("/guardar")
    @PreAuthorize("hasRole('Admin') ")
    public String guardarDetalleMantenimiento(DetalleMantenimientoEntity detalleMantenimiento) {
        // Guardar el detalle en la base de datos
        detalleMantenimientoRepository.save(detalleMantenimiento);

        // Redirigir al listado de detalles del mantenimiento
        return "redirect:/detalles/ver/" + detalleMantenimiento.getMantenimiento().getIdMantenimiento();  // Cambiar la ruta a /ver
    }

    // Ver detalles de un mantenimiento
    @GetMapping("/ver/{mantenimientoId}")  // Cambiar la ruta de /lista a /ver
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String listarDetallesMantenimiento(@PathVariable Long mantenimientoId, @RequestParam(name = "maquinaId", required = false) Long maquinaId, Model model) {
        MantenimientoEntity mantenimiento = mantenimientoRepository.findById(mantenimientoId).orElse(null);
        if (mantenimiento == null) {
            return "error_page"; // O redirigir a alguna página de error
        }

        // Obtener el detalle de mantenimiento relacionado
        DetalleMantenimientoEntity detalle = detalleMantenimientoRepository.findByMantenimiento(mantenimiento);

        if (detalle == null) {
            return "error_page";  // O redirigir a alguna página de error si no hay detalle
        }

        model.addAttribute("detalleMantenimiento", detalle);
        model.addAttribute("mantenimiento", mantenimiento);


        //----------------



        return "list_detalle_mantenimiento";  // Vista para mostrar detalles de mantenimiento
    }




    // Método para editar un detalle de mantenimiento
    @GetMapping("/editar/{detalleId}")
    @PreAuthorize("hasRole('Admin') ")
    public String mostrarFormularioDeEdicionDetalle(@PathVariable Long detalleId, Model model) {
        DetalleMantenimientoEntity detalle = detalleMantenimientoRepository.findById(detalleId).orElse(null);

        if (detalle == null) {
            return "error_page"; // Si no se encuentra el detalle
        }

        // Cargar el mantenimiento y la máquina relacionados
        MantenimientoEntity mantenimiento = detalle.getMantenimiento();
        MaquinaEntity maquina = mantenimiento.getMaquina();

        model.addAttribute("maquina", maquina);
        model.addAttribute("detalleMantenimiento", detalle);
        return "detalle_form";  // Vista para editar el detalle
    }

    // Guardar el detalle de mantenimiento editado
    @PostMapping("/actualizar")
    @PreAuthorize("hasRole('Admin') ")
    public String actualizarDetalleMantenimiento(DetalleMantenimientoEntity detalleMantenimiento) {
        // Guardar el detalle editado
        detalleMantenimientoRepository.save(detalleMantenimiento);

        // Redirigir al listado de detalles del mantenimiento
        return "redirect:/detalles/ver/" + detalleMantenimiento.getMantenimiento().getIdMantenimiento();
    }
/*
    @GetMapping("/borrar/{detalleId}")
    @PreAuthorize("hasRole('Admin')")
    public String borrarDetalleMantenimiento(@PathVariable Long detalleId) {
        DetalleMantenimientoEntity detalle = detalleMantenimientoRepository.findById(detalleId).orElse(null);

        if (detalle != null) {
            // Eliminar solo el detalle de mantenimiento
            detalleMantenimientoRepository.delete(detalle);
            logger.info("Detalle eliminado exitosamente.");
        } else {
            logger.error("No se encontró el detalle de mantenimiento con ID: {}", detalleId);
        }

        // Asegúrate de redirigir a la URL correcta usando el ID del mantenimiento
        Long mantenimientoId = detalle != null ? detalle.getMantenimiento().getIdMantenimiento() : null;
        if (mantenimientoId != null) {
            return "redirect:/detalles/ver/" + mantenimientoId;
        } else {
            return "redirect:/mantenimientos";  // Redirige a una lista general si no se encuentra el mantenimiento
        }


    }
  */




}

