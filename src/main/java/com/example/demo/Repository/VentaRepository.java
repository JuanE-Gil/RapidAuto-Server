package com.example.demo.Repository;

import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Integer> {

    Optional<VentaEntity> findByIdauto(AutoEntity idauto);

    @Modifying
    @Query("UPDATE VentaEntity e SET e.estado = :estado WHERE e.id_venta = :Id")
    void insertEstado(@Param("Id") Integer Id, @Param("estado") String estado);

}
