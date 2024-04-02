package com.example.demo.Repository;

import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.Posibles_ClienteEntity;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Model.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Posibles_ClientesEntityRepository extends JpaRepository<Posibles_ClienteEntity,Integer> {

    List<Posibles_ClienteEntity> findByUsuario(UsuarioEntity usuario);

    List<Posibles_ClienteEntity> findByAuto(AutoEntity auto);
}
