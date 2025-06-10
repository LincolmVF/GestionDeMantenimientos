package com.prototipo.prototipoapp.service.maquina;


import com.prototipo.prototipoapp.entity.maquina.MaquinaEntity;
import com.prototipo.prototipoapp.repository.maquina.MaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaquinaServiceImpl implements MaquinaService {

    @Autowired
    private MaquinaRepository maquinaRepository;


    @Override
    public List<MaquinaEntity> listarTodasLasMaquina() {
        return maquinaRepository.findAll();
    }

    @Override
    public MaquinaEntity guardarMaquina(MaquinaEntity maquinaEntity) {
        return maquinaRepository.save(maquinaEntity);
    }

    @Override
    public MaquinaEntity actualizarMaquina(MaquinaEntity maquinaEntity) {
        return maquinaRepository.save(maquinaEntity);
    }

    @Override
    public MaquinaEntity buscarMaquinaPorId(Long id) {
        return maquinaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarMaquina(Long id) {
maquinaRepository.deleteById(id);
    }

    @Override
    public List<MaquinaEntity> findByTipoEquipo(String tipoEquipo) {
        return maquinaRepository.findByTipoEquipo(tipoEquipo);
    }


    public List<Map<String, Object>> obtenerMantenimientosPorTipo() {
        List<Object[]> resultados = maquinaRepository.obtenerMantenimientosPorTipo();
        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Object[] fila : resultados) {
            Map<String, Object> mapa = new HashMap<>();
            mapa.put("tipo_equipo", fila[0]);
            mapa.put("total_correctivos", fila[1]);
            mapa.put("total_preventivos", fila[2]);
            respuesta.add(mapa);
        }
        return respuesta;
    }


}
