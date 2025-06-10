package com.prototipo.prototipoapp.controller.maquina;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prototipo.prototipoapp.entity.maquina.MaquinaEntity;
import com.prototipo.prototipoapp.repository.maquina.MantenimientoRepository;
import com.prototipo.prototipoapp.repository.maquina.MaquinaRepository;
import com.prototipo.prototipoapp.service.maquina.MaquinaService;
import com.prototipo.prototipoapp.service.maquina.TipoMaquinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/maquinas")
public class MaquinaController {

    @Autowired
    private MaquinaRepository maquinaRepository;
    @Autowired
    private TipoMaquinaService tipoMaquinaService;

@Autowired
private MaquinaService maquinaService;

@Autowired
    private MantenimientoRepository mantenimientoRepository;

@Autowired
private ObjectMapper objectMapper;



    @GetMapping("/nueva")
    @PreAuthorize("hasRole('Admin') ")
    public String mostrarFormularioDeNuevaMaquina(Model model) {
        model.addAttribute("maquina", new MaquinaEntity());
        model.addAttribute("tiposMaquina",tipoMaquinaService.listarTipoMaquinas());

        return "maquina_form";
    }

    @PostMapping("/guardar")
    @PreAuthorize("hasRole('Admin')")
    public String guardarMaquina(@ModelAttribute MaquinaEntity maquina,
                                 @RequestParam(value = "imagen", required = false) String imagenUrl) {
        try {
            if (imagenUrl != null && !imagenUrl.isEmpty()) {
                // Guardamos la URL de la imagen en lugar del archivo
                maquina.setImagen(imagenUrl);  // Asignar la URL proporcionada
            }
            // Asegurar que 'estado' tenga un valor predeterminado
            if (maquina.getEstado() == null || maquina.getEstado().isEmpty()) {
                System.out.println("Estado de maquina no definido");
                maquina.setEstado("No definido");  // Puedes cambiar esto por "ACTIVO" u otro valor
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        maquinaRepository.save(maquina);  // Guardar la máquina con la URL de la imagen
        return "redirect:/maquinas";
    }









    @GetMapping
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String listarMaquinas(@RequestParam(value = "tipoEquipo", required = false) String tipoEquipo,Model model) {
        List<MaquinaEntity> maquinas;
        if (tipoEquipo != null && !tipoEquipo.isEmpty()) {
            maquinas = maquinaRepository.findByTipoEquipo(tipoEquipo); // Llamada al servicio para obtener las máquinas filtradas
        } else {
            maquinas = maquinaRepository.findAll(); // Llamada al servicio para obtener todas las máquinas
        }
        model.addAttribute("tiposMaquina",tipoMaquinaService.listarTipoMaquinas());

        model.addAttribute("maquinas", maquinas);
        model.addAttribute("tipoEquipo", tipoEquipo);
        return "maquina_list";
    }

    // Redirigir al listado de mantenimientos de la máquina seleccionada
    @GetMapping("/{maquinaId}/mantenimientos")
    @PreAuthorize("hasRole('Admin') or hasRole('Usuario')")
    public String listarMantenimientosDeMaquina(@PathVariable Long maquinaId, Model model) {
        MaquinaEntity maquina = maquinaRepository.findById(maquinaId).orElse(null);
        if (maquina != null) {
            model.addAttribute("mantenimientos", mantenimientoRepository.findByMaquina(maquina));
            model.addAttribute("maquina", maquina);
        } else {
            model.addAttribute("error", "Máquina no encontrada");
        }
        return "mantenimiento_list"; // Asegúrate de tener la vista "mantenimiento_list" para mostrar los mantenimientos
    }
@GetMapping("/editar/{id}")
@PreAuthorize("hasRole('Admin') ")
    public String editarMaquina(@PathVariable Long id, Model model) {
        MaquinaEntity maquina = maquinaRepository.findById(id).orElse(null);
        if (maquina != null) {
            model.addAttribute("maquina", maquina);
            return "maquina_form";
        }else {
            model.addAttribute("error", "Maquina no encontrada");
        return "redirect:/maquinas";
        }
}

@PostMapping("editar/guardar")
@PreAuthorize("hasRole('Admin') ")
    public String guardarEdicionDeMaquina(MaquinaEntity maquina) {
        maquinaRepository.save(maquina);
        return "redirect:/maquinas";
}

@GetMapping("/borrar/{id}")
@PreAuthorize("hasRole('Admin') ")
    public String borrarMaquina(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try{
            maquinaRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensaje", "Maquina eliminada");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("mensaje", "Error al eliminar");
        }
        return "redirect:/maquinas";
}



//Dashboard

    @GetMapping("/por-tipo")
    public String obtenerMantenimientosPorTipo(Model model) throws JsonProcessingException {
        List<Map<String, Object>> mantenimientos = maquinaService.obtenerMantenimientosPorTipo();

        // Convertir lista a JSON
        String jsonMantenimientos = objectMapper.writeValueAsString(mantenimientos);

        // Pasamos el JSON al modelo para que lo use Thymeleaf
        model.addAttribute("mantenimientosJson", jsonMantenimientos);

        return "dashboard-por-tipo"; // Nombre del archivo HTML en templates
    }

}