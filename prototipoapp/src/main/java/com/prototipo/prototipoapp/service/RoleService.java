package com.prototipo.prototipoapp.service;

import com.prototipo.prototipoapp.entity.RoleEntity;

import java.util.List;

public interface RoleService {
    List<RoleEntity> listarRoles();
    RoleEntity buscarRolePorId(Long id);
    RoleEntity guardarRole(RoleEntity role);
    RoleEntity actualizarRole(RoleEntity role);
    void eliminarRole(Long id);
}
