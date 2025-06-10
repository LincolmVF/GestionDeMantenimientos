package com.prototipo.prototipoapp.service.maquina;


import com.prototipo.prototipoapp.entity.maquina.MantenimientoEntity;

import java.util.List;

public interface MantenimientoService {
   List<MantenimientoEntity> listarTodasLosMantenimientos();
   MantenimientoEntity guardarMantenimiento(MantenimientoEntity mantenimientoEntity);
     MantenimientoEntity actualizarMantenimiento(MantenimientoEntity mantenimientoEntity);

     MantenimientoEntity buscarMantenimientoPorId(Long id);
     void eliminarMantenimiento(Long id);
}
