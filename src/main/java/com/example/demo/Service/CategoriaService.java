package com.example.demo.Service;


import com.example.demo.DTO.CategoriaDTO;
import com.example.demo.Model.CategoriaEntity;
import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Service.Compresor.ImageCompressor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImageCompressor imageCompressor;

    public List<CategoriaEntity> listar_categoria()throws Exception{
        return categoriaRepository.findAll();
    }

    @Transactional(rollbackOn = Exception.class)
    public CategoriaEntity registrar_categoria(CategoriaDTO dto)throws Exception{
        CategoriaEntity categoriaEntity = new CategoriaEntity();

        categoriaEntity.setDescripcion(dto.getDescripcion());

        byte[] img = imageCompressor.compressImage(dto.getImg().getBytes());
        categoriaEntity.setImg(img);

        categoriaRepository.save(categoriaEntity);
        return categoriaEntity;
    }

    @Transactional(rollbackOn = Exception.class)
    public CategoriaEntity actualizar_categoria(CategoriaDTO dto,Integer Id)throws Exception {
        Optional<CategoriaEntity> optional = categoriaRepository.findById(Id);
        if(optional.isPresent()){
            CategoriaEntity categoriaEntity = optional.get();
            categoriaEntity.setDescripcion(dto.getDescripcion());
            categoriaEntity.setImg(dto.getImg().getBytes());
            categoriaRepository.save(categoriaEntity);
            return categoriaEntity;
        }else {
            return null;
        }
    }
    @Transactional(rollbackOn = Exception.class)
    public void eliminar(Integer id) throws Exception{
        Optional<CategoriaEntity> optional = categoriaRepository.findById(id);
        if (optional.isPresent()){
            categoriaRepository.delete(optional.get());
        }
    }



}
