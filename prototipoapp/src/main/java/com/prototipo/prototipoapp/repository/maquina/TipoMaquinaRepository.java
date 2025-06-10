package com.prototipo.prototipoapp.repository.maquina;

import com.prototipo.prototipoapp.entity.maquina.TipoMaquina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMaquinaRepository extends JpaRepository<TipoMaquina, Long> {

boolean existsByName(String nome);
}
