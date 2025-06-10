package com.prototipo.prototipoapp.service;

import com.prototipo.prototipoapp.entity.RoleEntity;
import com.prototipo.prototipoapp.entity.UserEntity;

import java.util.List;

public interface UserService {
List<UserEntity> listarTodosUsuarios();
UserEntity guardadUsuario(UserEntity usuario);
UserEntity actualizarUsuario(UserEntity usuario);
UserEntity buscarUsuarioPorid(Long id);
void eliminarUsuario(Long id);
     boolean existsAdmin();



}
