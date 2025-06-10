package com.prototipo.prototipoapp.service.maquina;

import com.prototipo.prototipoapp.entity.maquina.TipoMaquina;
import com.prototipo.prototipoapp.repository.maquina.TipoMaquinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoMaquinaServiceImpl implements TipoMaquinaService {
@Autowired
    private TipoMaquinaRepository tipoMaquinaRepository;

    @Override
    public List<TipoMaquina> listarTipoMaquinas() {
        return tipoMaquinaRepository.findAll();
    }

    @Override
    public boolean agregarTipoMaquina(String nombre) {
        if (!tipoMaquinaRepository.existsByName(nombre)) {
            tipoMaquinaRepository.save(new TipoMaquina(nombre));
            return true;
        }
        return false; // Si el tipo ya existe, no lo agrega
    }

    @Override
    public boolean actualizarTipoMaquina(Long idTipoMaquina, String nombre) {
        Optional<TipoMaquina> tipoOptional = tipoMaquinaRepository.findById(idTipoMaquina);
        if (tipoOptional.isEmpty()) {
            return false;
        }
        TipoMaquina tipo = tipoOptional.get();
        tipo.setName(nombre);
        tipoMaquinaRepository.save(tipo);
        return true;
    }

    @Override
    public boolean eliminarTipoMaquina(Long idTipoMaquina) {
        Optional<TipoMaquina> tipoOptional = tipoMaquinaRepository.findById(idTipoMaquina);
        if (tipoOptional.isEmpty()) {
            return false;
        }
        tipoMaquinaRepository.deleteById(idTipoMaquina);
        return true;
    }
}
