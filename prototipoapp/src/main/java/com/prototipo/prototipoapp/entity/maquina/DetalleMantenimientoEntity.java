package com.prototipo.prototipoapp.entity.maquina;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class DetalleMantenimientoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalleMan;

    @NotNull
    private String descripcionDetalle;

    @OneToOne
    @JoinColumn(name = "mantenimiento_id", nullable = false)
    private MantenimientoEntity mantenimiento;

    // Getters y Setters
    public Long getIdDetalleMan() {
        return idDetalleMan;
    }

    public void setIdDetalleMan(Long idDetalleMan) {
        this.idDetalleMan = idDetalleMan;
    }

    public String getDescripcionDetalle() {
        return descripcionDetalle;
    }

    public void setDescripcionDetalle(String descripcionDetalle) {
        this.descripcionDetalle = descripcionDetalle;
    }

    public MantenimientoEntity getMantenimiento() {
        return mantenimiento;
    }

    public void setMantenimiento(MantenimientoEntity mantenimiento) {
        this.mantenimiento = mantenimiento;
    }
}
