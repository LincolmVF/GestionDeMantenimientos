package com.prototipo.prototipoapp.controller.checklist;

import com.prototipo.prototipoapp.entity.maquina.MantenimientoEntity;
import com.prototipo.prototipoapp.entity.maquina.MaquinaEntity;
import com.prototipo.prototipoapp.repository.maquina.MantenimientoRepository;
import com.prototipo.prototipoapp.repository.maquina.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/checklist")
public class checklistController {
    @Autowired
    private MaquinaRepository maquinaRepository;

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @GetMapping
    public String showChecklist(@RequestParam(required = false) Integer year, Model model) {
        // If year is not provided, use current year
        int selectedYear = (year != null) ? year : Year.now().getValue();

        List<MaquinaEntity> maquinas = maquinaRepository.findAll();

        // Create month labels
        String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        // Create maintenance status map
        Map<Long, boolean[]> maintenanceStatus = new HashMap<>();

        // For each machine, check maintenance status for each month
        for (MaquinaEntity maquina : maquinas) {
            boolean[] monthStatus = new boolean[12];
            List<MantenimientoEntity> mantenimientos = mantenimientoRepository
                    .findByMaquinaAndYear(maquina.getIdMaquina(), selectedYear);

            for (MantenimientoEntity mant : mantenimientos) {
                if ("Preventivo".equals(mant.getTipoMantenimiento())) {
                    LocalDate fechaMantenimiento = mant.getFechaMantenimiento();
                    int month = fechaMantenimiento.getMonthValue() - 1; // Adjust for 0-based array
                    monthStatus[month] = true;
                }
            }

            maintenanceStatus.put(maquina.getIdMaquina(), monthStatus);
        }

        // Generate a list of years for the dropdown (e.g., current year +/- 5 years)
        int currentYear = Year.now().getValue();
        List<Integer> yearOptions = IntStream.rangeClosed(currentYear - 5, currentYear + 5)
                .boxed().collect(Collectors.toList());

        model.addAttribute("maquinas", maquinas);
        model.addAttribute("months", months);
        model.addAttribute("maintenanceStatus", maintenanceStatus);
        model.addAttribute("selectedYear", selectedYear);
        model.addAttribute("yearOptions", yearOptions);

        return "checklist";
    }
}








