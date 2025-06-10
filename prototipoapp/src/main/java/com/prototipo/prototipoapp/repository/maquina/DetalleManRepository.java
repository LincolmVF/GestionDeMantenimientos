package com.prototipo.prototipoapp.repository.maquina;


import com.prototipo.prototipoapp.entity.maquina.DetalleMantenimientoEntity;
import com.prototipo.prototipoapp.entity.maquina.MantenimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleManRepository extends JpaRepository<DetalleMantenimientoEntity,Long> {


    // Modificar el método para que devuelva un solo detalle, no una lista
    DetalleMantenimientoEntity findByMantenimiento(MantenimientoEntity mantenimiento);
}
