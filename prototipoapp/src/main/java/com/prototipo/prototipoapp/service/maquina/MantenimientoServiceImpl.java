package com.prototipo.prototipoapp.service.maquina;


import com.prototipo.prototipoapp.entity.maquina.MantenimientoEntity;
import com.prototipo.prototipoapp.repository.maquina.MantenimientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MantenimientoServiceImpl implements MantenimientoService {

    private MantenimientoRepository mantenimientoRepository;

    @Override
    public List<MantenimientoEntity> listarTodasLosMantenimientos() {
        return mantenimientoRepository.findAll();
    }

    @Override
    public MantenimientoEntity guardarMantenimiento(MantenimientoEntity mantenimientoEntity) {
        return mantenimientoRepository.save(mantenimientoEntity);
    }

    @Override
    public MantenimientoEntity actualizarMantenimiento(MantenimientoEntity mantenimientoEntity) {
        return mantenimientoRepository.save(mantenimientoEntity);
    }

    @Override
    public MantenimientoEntity buscarMantenimientoPorId(Long id) {
        return mantenimientoRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarMantenimiento(Long id) {
mantenimientoRepository.deleteById(id);
    }
}
