package com.prototipo.prototipoapp.entity.maquina;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class MantenimientoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMantenimiento;

    @NotNull
    private Double costo;

    @NotNull

    private String autorArreglo;

    @NotNull
    private LocalDate fechaMantenimiento;

    @NotNull
    private String tipoMantenimiento;

    @NotNull
    private String ordenDeCompra;

    @ManyToOne
    @JoinColumn(name = "maquina_id", nullable = false)
    private MaquinaEntity maquina;

    @OneToOne(mappedBy = "mantenimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private DetalleMantenimientoEntity detalleMantenimiento;

    // Getters y Setters


    public String getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public void setOrdenDeCompra(String ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }

    public Long getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(Long idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getAutorArreglo() {
        return autorArreglo;
    }

    public void setAutorArreglo(String autorArreglo) {
        this.autorArreglo = autorArreglo;
    }

    public LocalDate getFechaMantenimiento() {
        return fechaMantenimiento;
    }

    public void setFechaMantenimiento(LocalDate fechaMantenimiento) {
        this.fechaMantenimiento = fechaMantenimiento;
    }

    public String getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    public void setTipoMantenimiento(String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    public MaquinaEntity getMaquina() {
        return maquina;
    }

    public void setMaquina(MaquinaEntity maquina) {
        this.maquina = maquina;
    }

    public DetalleMantenimientoEntity getDetalleMantenimiento() {
        return detalleMantenimiento;
    }

    public void setDetalleMantenimiento(DetalleMantenimientoEntity detalleMantenimiento) {
        this.detalleMantenimiento = detalleMantenimiento;
    }

    public MantenimientoEntity(Long idMantenimiento, Double costo, String autorArreglo, LocalDate fechaMantenimiento, String tipoMantenimiento, MaquinaEntity maquina, DetalleMantenimientoEntity detalleMantenimiento,String ordenDeCompra) {
        this.idMantenimiento = idMantenimiento;
        this.costo = costo;
        this.autorArreglo = autorArreglo;
        this.fechaMantenimiento = fechaMantenimiento;
        this.tipoMantenimiento = tipoMantenimiento;
        this.maquina = maquina;
        this.detalleMantenimiento = detalleMantenimiento;
        this.ordenDeCompra = ordenDeCompra;
    }

    public MantenimientoEntity() {
    }

    public MantenimientoEntity(Double costo, String autorArreglo, LocalDate fechaMantenimiento, String tipoMantenimiento, MaquinaEntity maquina, DetalleMantenimientoEntity detalleMantenimiento,String ordenDeCompra) {
        this.costo = costo;
        this.autorArreglo = autorArreglo;
        this.fechaMantenimiento = fechaMantenimiento;
        this.tipoMantenimiento = tipoMantenimiento;
        this.maquina = maquina;
        this.detalleMantenimiento = detalleMantenimiento;
        this.ordenDeCompra = ordenDeCompra;
    }

    public MantenimientoEntity(MaquinaEntity maquina, String tipoMantenimiento, LocalDate fechaMantenimiento, String autorArreglo, Double costo,String ordenDeCompra) {
        this.maquina = maquina;
        this.tipoMantenimiento = tipoMantenimiento;
        this.fechaMantenimiento = fechaMantenimiento;
        this.autorArreglo = autorArreglo;
        this.costo = costo;
        this.ordenDeCompra = ordenDeCompra;
    }

    public MantenimientoEntity(String tipoMantenimiento, LocalDate fechaMantenimiento, String autorArreglo, Double costo,String ordenDeCompra) {
        this.tipoMantenimiento = tipoMantenimiento;
        this.fechaMantenimiento = fechaMantenimiento;
        this.autorArreglo = autorArreglo;
        this.costo = costo;
        this.ordenDeCompra = ordenDeCompra;
    }
}
