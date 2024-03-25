package com.example.demo.Service;

import com.example.demo.Model.RolEntity;
import com.example.demo.Repository.RolRepsitory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepsitory rolRepsitory;

    public Integer buscarRol(String descripcion)throws Exception{
        RolEntity rolEntity = rolRepsitory.findByDescripcion(descripcion)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        return rolEntity.getIdRol();
    }
}
