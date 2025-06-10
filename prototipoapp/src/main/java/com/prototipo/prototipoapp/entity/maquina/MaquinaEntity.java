package com.prototipo.prototipoapp.entity.maquina;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class MaquinaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaquina;

    @NotNull
    private Double costoEquipo;


    private String descripcion;

    @NotNull

    private String nombre;

    @NotNull
    private String numeroDeActivo;
    @NotNull
    private String numeroSAP;
    @NotNull
    private String tipoEquipo;
    @NotNull
    private String dimenciones;

    @OneToMany(mappedBy = "maquina", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MantenimientoEntity> mantenimientos;
    // Nuevo campo para almacenar la imagen en la base de datos
    @Column(name = "imagen")
    private String imagen;

    @Column(name = "estado")
    private String estado;
    // Getters y Setters


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public  String getDimenciones() {
        return dimenciones;
    }

    public void setDimenciones( String dimenciones) {
        this.dimenciones = dimenciones;
    }

    public @NotNull Double getCostoEquipo() {
        return costoEquipo;
    }

    public void setCostoEquipo(@NotNull Double costoEquipo) {
        this.costoEquipo = costoEquipo;
    }

    public @NotNull String getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(@NotNull String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public @NotNull String getNumeroDeActivo() {
        return numeroDeActivo;
    }

    public void setNumeroDeActivo(@NotNull String numeroDeActivo) {
        this.numeroDeActivo = numeroDeActivo;
    }

    public @NotNull String getNumeroSAP() {
        return numeroSAP;
    }

    public void setNumeroSAP(@NotNull String numeroSAP) {
        this.numeroSAP = numeroSAP;
    }

    public Long getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(Long idMaquina) {
        this.idMaquina = idMaquina;
    }



    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MantenimientoEntity> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(List<MantenimientoEntity> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    public MaquinaEntity(Long idMaquina, Double costoEquipo, String descripcion, String nombre, List<MantenimientoEntity> mantenimientos,String numeroDeActivo,String numeroSAP,String tipoEquipo, String dimenciones,String imagen,String estado) {
        this.idMaquina = idMaquina;
        this.costoEquipo = costoEquipo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.mantenimientos = mantenimientos;
this.numeroDeActivo = numeroDeActivo;
this.numeroSAP = numeroSAP;
this.tipoEquipo = tipoEquipo;
this.dimenciones = dimenciones;
this.imagen = imagen;
this.estado = estado;
    }
    public MaquinaEntity() {

    }

    public MaquinaEntity(Double costoEquipo, String descripcion, String nombre, List<MantenimientoEntity> mantenimientos,String numeroDeActivo,String numeroSAP,String tipoEquipo, String dimenciones,String imagen,String estado) {
        this.costoEquipo = costoEquipo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.mantenimientos = mantenimientos;
        this.numeroDeActivo = numeroDeActivo;
        this.numeroSAP = numeroSAP;
        this.tipoEquipo = tipoEquipo;
        this.dimenciones = dimenciones;
        this.imagen = imagen;
        this.estado = estado;
    }

    public MaquinaEntity(String nombre, String descripcion, Double costoEquipo,String numeroDeActivo,String numeroSAP,String tipoEquipo, String dimenciones,String imagen,String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoEquipo = costoEquipo;
        this.numeroDeActivo = numeroDeActivo;
        this.numeroSAP = numeroSAP;
        this.tipoEquipo = tipoEquipo;
        this.dimenciones = dimenciones;
        this.imagen = imagen;
        this.estado = estado;
    }


}

