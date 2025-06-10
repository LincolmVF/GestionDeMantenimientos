package com.prototipo.prototipoapp.entity;


import jakarta.persistence.*;

@Entity
@Table(name="permissions")
public class PermisionEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PermisionEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public PermisionEntity(String name) {
        this.name = name;
    }
    public PermisionEntity() {}


}
