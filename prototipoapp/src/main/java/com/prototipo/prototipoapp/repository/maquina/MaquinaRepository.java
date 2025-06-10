package com.prototipo.prototipoapp.repository.maquina;


import com.prototipo.prototipoapp.entity.maquina.MaquinaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaquinaRepository extends JpaRepository<MaquinaEntity,Long> {

    @Query("SELECT m FROM MaquinaEntity m WHERE m.tipoEquipo = :tipoEquipo")
    List<MaquinaEntity> findByTipoEquipo(@Param("tipoEquipo") String tipoEquipo);

    @Query(value = "SELECT m.tipo_equipo, " +
            "SUM(CASE WHEN mt.tipo_mantenimiento = 'Correctivo' THEN 1 ELSE 0 END) AS total_correctivos, " +
            "SUM(CASE WHEN mt.tipo_mantenimiento = 'Preventivo' THEN 1 ELSE 0 END) AS total_preventivos " +
            "FROM maquina_entity m " +
            "JOIN mantenimiento_entity mt ON m.id_maquina = mt.maquina_id " +
            "GROUP BY m.tipo_equipo", nativeQuery = true)
    List<Object[]> obtenerMantenimientosPorTipo();

}
