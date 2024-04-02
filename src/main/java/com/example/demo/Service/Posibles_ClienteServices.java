package com.example.demo.Service;


import com.example.demo.DTO.Posibles_ClienteDTO;
import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.Posibles_ClienteEntity;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.AutoRepository;
import com.example.demo.Repository.Posibles_ClientesEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@Slf4j
public class Posibles_ClienteServices {

    @Autowired
    private Posibles_ClientesEntityRepository posiblesClientesEntityRepository;

    @Autowired
    private AutoRepository autoRepository;


    public List<Posibles_ClienteEntity> listar()throws Exception{
        return posiblesClientesEntityRepository.findAll();
    }

    public  List<Posibles_ClienteEntity> listar_Mensaje_del_Receptor(Integer usuario)throws Exception{
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId_usuario(usuario);
        log.trace("Mensaje de id auto: " + obtenerPrimerAuto(usuarioEntity));
        return posiblesClientesEntityRepository.findByAuto(obtenerPrimerAuto(usuarioEntity));

    }

    public List<Posibles_ClienteEntity> ListarPorUsuario(Integer usuario)throws Exception{
        return posiblesClientesEntityRepository.findByUsuario(new UsuarioEntity(usuario));
    }

    @Transactional(rollbackOn = Exception.class)
    public Posibles_ClienteEntity registrar(Posibles_ClienteDTO posiblesClienteDTO, AutoEntity auto, Integer Usuario )throws Exception{
        Posibles_ClienteEntity posiblesClienteEntity = new Posibles_ClienteEntity();
        posiblesClienteEntity.setAuto(auto);
        posiblesClienteEntity.setUsuario(new UsuarioEntity(Usuario));
        posiblesClienteEntity.setDescripcion(posiblesClienteDTO.getDescripcion());
        return this.posiblesClientesEntityRepository.save(posiblesClienteEntity);
    }

    public AutoEntity obtenerPrimerAuto(UsuarioEntity usuario) {
        List<AutoEntity> listaAutos = autoRepository.findByIdusuarioAndEstatus(usuario, true);
        if (!listaAutos.isEmpty()) {
            log.trace("Mensaje de id auto: " + listaAutos.get(0));
            return listaAutos.get(0);
        } else {
            return null;
        }
    }


}
