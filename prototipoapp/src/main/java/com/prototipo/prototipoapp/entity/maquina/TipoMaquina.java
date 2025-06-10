package com.prototipo.prototipoapp.entity.maquina;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_maquina")
public class TipoMaquina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTipoMaquina;
    @Column(unique = true, nullable = false)
    private String name;

    public TipoMaquina() {
    }

    public TipoMaquina(String name) {
        this.name = name;
    }

    public TipoMaquina(int idTipoMaquina, String name) {
        this.idTipoMaquina = idTipoMaquina;
        this.name = name;
    }

    public int getIdTipoMaquina() {
        return idTipoMaquina;
    }

    public void setIdTipoMaquina(int idTipoMaquina) {
        this.idTipoMaquina = idTipoMaquina;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
