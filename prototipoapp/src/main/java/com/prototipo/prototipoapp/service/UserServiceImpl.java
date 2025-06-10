package com.prototipo.prototipoapp.service;

import com.prototipo.prototipoapp.entity.RoleEntity;
import com.prototipo.prototipoapp.entity.UserEntity;
import com.prototipo.prototipoapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserEntity> listarTodosUsuarios() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity guardadUsuario(UserEntity usuario) {
        System.out.println("Guardando usuario: " + usuario.getUsername());

        // Encriptar la contraseña antes de guardarla
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);  // Aquí estamos configurando la contraseña encriptada

        // Guardar el usuario con la contraseña encriptada
        return userRepository.save(usuario);
    }


    @Override
    public UserEntity actualizarUsuario(UserEntity usuario) {
        return userRepository.save(usuario);
    }

    @Override
    public UserEntity buscarUsuarioPorid(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarUsuario(Long id) {
userRepository.deleteById(id);
    }

    @Override
    public boolean existsAdmin() {
        return userRepository.existsByRoleNombre("Admin"); // Usa el nombre correcto del rol
    }


    // ✅ Bloquear la creación de más de un ADMIN


}
