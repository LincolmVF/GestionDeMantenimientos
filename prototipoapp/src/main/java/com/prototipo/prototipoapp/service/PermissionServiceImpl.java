package com.prototipo.prototipoapp.service;

import com.prototipo.prototipoapp.entity.PermisionEntity;
import com.prototipo.prototipoapp.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService{
   @Autowired
   private PermissionRepository permissionRepository;



    @Override
    public List<PermisionEntity> listarPermisos() {
        return permissionRepository.findAll();
    }

    @Override
    public PermisionEntity guardarPermiso(PermisionEntity permision) {
        return permissionRepository.save(permision);
    }

    @Override
    public PermisionEntity actualizarPermiso(PermisionEntity permision) {
        return permissionRepository.save(permision);
    }

    @Override
    public PermisionEntity buscarPermiso(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarPermiso(Long id) {
permissionRepository.deleteById(id);
    }
}
