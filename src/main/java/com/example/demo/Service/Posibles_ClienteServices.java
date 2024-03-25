package com.example.demo.Service;


import com.example.demo.DTO.Posibles_ClienteDTO;
import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.Posibles_ClienteEntity;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.Posibles_ClientesEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Posibles_ClienteServices {

    @Autowired
    private Posibles_ClientesEntityRepository posiblesClientesEntityRepository;




    public List<Posibles_ClienteEntity> listar()throws Exception{
        return posiblesClientesEntityRepository.findAll();
    }


    public List<Posibles_ClienteEntity> ListarPorUsuario(Integer usuario)throws Exception{
        return posiblesClientesEntityRepository.findByUsuario(new UsuarioEntity(usuario));
    }

    @Transactional(rollbackOn = Exception.class)
    public Posibles_ClienteEntity registrar(Posibles_ClienteDTO posiblesClienteDTO, int auto, Integer Usuario )throws Exception{
        Posibles_ClienteEntity posiblesClienteEntity = new Posibles_ClienteEntity();
        posiblesClienteEntity.setAuto(auto);
        posiblesClienteEntity.setUsuario(new UsuarioEntity(Usuario));
        posiblesClienteEntity.setDescripcion(posiblesClienteDTO.getDescripcion());
        return this.posiblesClientesEntityRepository.save(posiblesClienteEntity);
    }
}
