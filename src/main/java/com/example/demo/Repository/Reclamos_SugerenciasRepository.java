package com.example.demo.Repository;

import com.example.demo.Model.Reclamos_SugerenciasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Reclamos_SugerenciasRepository extends JpaRepository<Reclamos_SugerenciasEntity,Integer> {
}
