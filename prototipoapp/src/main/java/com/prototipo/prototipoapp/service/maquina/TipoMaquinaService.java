package com.prototipo.prototipoapp.service.maquina;

import com.prototipo.prototipoapp.entity.maquina.TipoMaquina;

import java.util.List;
public interface TipoMaquinaService {

List<TipoMaquina> listarTipoMaquinas();
boolean agregarTipoMaquina(String nombre);
 boolean actualizarTipoMaquina(Long idTipoMaquina, String nombre);
boolean eliminarTipoMaquina(Long idTipoMaquina);
}
