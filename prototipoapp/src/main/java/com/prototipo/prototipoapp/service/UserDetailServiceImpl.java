package com.prototipo.prototipoapp.service;

import com.prototipo.prototipoapp.entity.UserEntity;
import com.prototipo.prototipoapp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario en la base de datos
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        // Lista de autoridades (roles y permisos)
        List<GrantedAuthority> authorityList = new ArrayList<>();

        // Añadir el rol del usuario (debe empezar con "ROLE_")
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().getNombreRol()));

        // Añadir permisos asociados al rol
        userEntity.getRole().getPermisionList().forEach(permission ->
                authorityList.add(new SimpleGrantedAuthority(permission.getName()))
        );

        // Devuelve el objeto User de Spring Security
        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList
        );
    }
}
