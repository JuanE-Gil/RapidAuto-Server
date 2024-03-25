package com.example.demo.Repository;

import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Integer> {

    Optional<VentaEntity> findByIdauto(AutoEntity idauto);

}
