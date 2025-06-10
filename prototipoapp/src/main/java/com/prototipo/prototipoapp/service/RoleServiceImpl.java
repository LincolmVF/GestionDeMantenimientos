package com.prototipo.prototipoapp.service;

import com.prototipo.prototipoapp.entity.RoleEntity;
import com.prototipo.prototipoapp.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService{


    @Autowired
    private RoleRepository roleRepository;



    @Override
    public List<RoleEntity> listarRoles() {
        return roleRepository.findAll();
    }

    @Override
    public RoleEntity buscarRolePorId(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public RoleEntity guardarRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public RoleEntity actualizarRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public void eliminarRole(Long id) {
roleRepository.deleteById(id);
    }
}
