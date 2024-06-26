package com.example.demo.Service;


import com.example.demo.DTO.Reclamos_SugerenciasDTO;
import com.example.demo.Model.Reclamos_SugerenciasEntity;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.Reclamos_SugerenciasRepository;
import com.example.demo.Service.Compresor.ImageCompressor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class Reclamos_SugerenciasService {

    @Autowired
    private Reclamos_SugerenciasRepository reclamosSugerenciasRepository;

    @Autowired
    private ImageCompressor imageCompressor;

    public List<Reclamos_SugerenciasEntity>  Listar()throws Exception {
          return  reclamosSugerenciasRepository.findAll();
    }

    @Transactional(rollbackOn =  Exception.class)
    public Reclamos_SugerenciasEntity registrar(Reclamos_SugerenciasDTO reclamosSugerenciasDTO, Integer usuario)throws Exception{
        Reclamos_SugerenciasEntity reclamosSugerenciasEntity = new Reclamos_SugerenciasEntity();

        String dateTime = DateTimeFormatter.ofPattern("MMM dd yyyy")
                .format(LocalDateTime.now());

        reclamosSugerenciasEntity.setUsuario(new UsuarioEntity(usuario));
        reclamosSugerenciasEntity.setFecha(dateTime);
        reclamosSugerenciasEntity.setMensaje(reclamosSugerenciasDTO.getMensaje());

        if (reclamosSugerenciasDTO.getImg() != null) {
            //byte[] img = imageCompressor.compressImage(reclamosSugerenciasDTO.getImg().getBytes());

            reclamosSugerenciasEntity.setImg(reclamosSugerenciasDTO.getImg().getBytes());
        }else {
            reclamosSugerenciasEntity.setImg(null);
        }



        return this.reclamosSugerenciasRepository.save(reclamosSugerenciasEntity);
    }


}
