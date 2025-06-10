package com.prototipo.prototipoapp.service.maquina;





import com.prototipo.prototipoapp.entity.maquina.DetalleMantenimientoEntity;

import java.util.List;

public interface DetalleMantenimientoService {
     List<DetalleMantenimientoEntity> listarTodasLosDetallesMantenimientos();
     DetalleMantenimientoEntity guardarDetalleMantenimiento(DetalleMantenimientoEntity detalleMantenimientoEntity);
     DetalleMantenimientoEntity actualizarDetalleMantenimiento(DetalleMantenimientoEntity detalleMantenimientoEntity);
     DetalleMantenimientoEntity buscarDetalleMantenimientoPorId(Long id);
     void eliminarDetalleMantenimiento(Long id);
}
