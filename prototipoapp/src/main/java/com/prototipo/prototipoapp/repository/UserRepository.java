package com.prototipo.prototipoapp.repository;

import com.prototipo.prototipoapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findUserEntityByUsername(String username);


  @Query("SELECT COUNT(u) > 0 FROM UserEntity u WHERE u.role.nombreRol = :nombreRol")
  boolean existsByRoleNombre(@Param("nombreRol") String nombreRol);



}
