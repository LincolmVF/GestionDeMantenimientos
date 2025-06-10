package com.prototipo.prototipoapp.entity;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
 @Column(name = "role_name", unique = true)

    private String nombreRol;
 @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
 @JoinTable(
         name = "role_permission",
         joinColumns = @JoinColumn(name = "role_id"),
         inverseJoinColumns = @JoinColumn(name = "permission_id")

 )
 private Set<PermisionEntity> permisionList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }



    public Set<PermisionEntity> getPermisionList() {
        return permisionList;
    }

    public void setPermisionList(Set<PermisionEntity> permisionList) {
        this.permisionList = permisionList;
    }

    public RoleEntity(Long id, String nombreRol, Set<PermisionEntity> permisionList) {
        this.id = id;
        this.nombreRol = nombreRol;
        this.permisionList = permisionList;
    }

    public RoleEntity(String roleEnum, Set<PermisionEntity> permisionList) {
        this.nombreRol = nombreRol;
        this.permisionList = permisionList;
    }
    public RoleEntity() {}
}
