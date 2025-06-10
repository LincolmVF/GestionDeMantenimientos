package com.prototipo.prototipoapp.service;

import com.prototipo.prototipoapp.entity.PermisionEntity;

import java.util.List;

public interface PermissionService {

    List<PermisionEntity> listarPermisos();
    PermisionEntity guardarPermiso(PermisionEntity permision);
   PermisionEntity actualizarPermiso(PermisionEntity permision);
   PermisionEntity buscarPermiso(Long id);
    void eliminarPermiso(Long id);
}
