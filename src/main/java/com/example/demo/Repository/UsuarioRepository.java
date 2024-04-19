package com.example.demo.Repository;

import com.example.demo.Model.RolEntity;
import com.example.demo.Model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByNombre(String nombre);

    Optional<UsuarioEntity> findByUsername(String username);

    List<UsuarioEntity> findByIdRol(RolEntity rol);

    Optional<UsuarioEntity> findByEstado(String username);

    Optional<UsuarioEntity> findByCorreoElectronico(String correo);

    Optional<UsuarioEntity> findByContrasena(String contrasena);

    @Modifying
    @Query("UPDATE UsuarioEntity e SET e.estado = :estado WHERE e.id_usuario = :Id")
    void update_estado(@Param("Id") Integer Id, @Param("estado") String estado);


    @Modifying
    @Query("UPDATE UsuarioEntity e SET e.contrasena = :contraseña WHERE e.id_usuario = :Id")
    void resetpassword(@Param("Id") Integer Id, @Param("contraseña") String contrasena);

}
