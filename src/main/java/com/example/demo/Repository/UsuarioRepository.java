package com.example.demo.Repository;

import com.example.demo.Model.RolEntity;
import com.example.demo.Model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByNombre(String nombre);

    Optional<UsuarioEntity> findByUsername(String username);

    List<UsuarioEntity> findByIdRol(RolEntity rol);

    Optional<UsuarioEntity> findByCorreoElectronico(String correo);

    Optional<UsuarioEntity> findByContrasena(String contrasena);

}
