package com.prototipo.prototipoapp.service.maquina;

import com.prototipo.prototipoapp.entity.maquina.DetalleMantenimientoEntity;
import com.prototipo.prototipoapp.repository.maquina.DetalleManRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleMantenimientoServiceImpl implements DetalleMantenimientoService {

    private DetalleManRepository detalleManRepository;

    @Override
    public List<DetalleMantenimientoEntity> listarTodasLosDetallesMantenimientos() {
        return detalleManRepository.findAll();
    }

    @Override
    public DetalleMantenimientoEntity guardarDetalleMantenimiento(DetalleMantenimientoEntity detalleMantenimientoEntity) {
        return detalleManRepository.save(detalleMantenimientoEntity);
    }

    @Override
    public DetalleMantenimientoEntity actualizarDetalleMantenimiento(DetalleMantenimientoEntity detalleMantenimientoEntity) {
        return detalleManRepository.save(detalleMantenimientoEntity);
    }

    @Override
    public DetalleMantenimientoEntity buscarDetalleMantenimientoPorId(Long id) {
        return detalleManRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarDetalleMantenimiento(Long id) {
detalleManRepository.deleteById(id);
    }
}
