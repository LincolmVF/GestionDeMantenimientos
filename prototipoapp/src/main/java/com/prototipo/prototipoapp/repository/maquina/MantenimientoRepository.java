package com.prototipo.prototipoapp.repository.maquina;



import com.prototipo.prototipoapp.entity.maquina.MantenimientoEntity;
import com.prototipo.prototipoapp.entity.maquina.MaquinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MantenimientoRepository extends JpaRepository<MantenimientoEntity,Long> {
    List<MantenimientoEntity> findByMaquina(MaquinaEntity maquina);

    @Query(value = "SELECT * FROM mantenimiento_entity WHERE maquina_id = :maquinaId AND YEAR(fecha_mantenimiento) = :year", nativeQuery = true)
    List<MantenimientoEntity> findByMaquinaAndYear(@Param("maquinaId") Long maquinaId, @Param("year") int year);


}
