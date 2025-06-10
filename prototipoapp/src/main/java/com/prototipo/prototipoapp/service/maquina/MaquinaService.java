package com.prototipo.prototipoapp.service.maquina;



import com.prototipo.prototipoapp.entity.maquina.MaquinaEntity;

import java.util.List;
import java.util.Map;

public interface MaquinaService {
     List<MaquinaEntity> listarTodasLasMaquina();
     MaquinaEntity guardarMaquina(MaquinaEntity maquinaEntity);
     MaquinaEntity actualizarMaquina(MaquinaEntity maquinaEntity);

     MaquinaEntity buscarMaquinaPorId(Long id);
     void eliminarMaquina(Long id);

     public List<MaquinaEntity> findByTipoEquipo(String tipoEquipo);

     List<Map<String, Object>> obtenerMantenimientosPorTipo();
}
