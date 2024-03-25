package com.example.demo.Repository;

import com.example.demo.Model.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepsitory extends JpaRepository<RolEntity,Integer> {


    Optional<RolEntity> findByDescripcion(String descripcion);
}
