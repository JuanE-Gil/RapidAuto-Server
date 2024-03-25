package com.example.demo.Service;

import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.VentaEntity;
import com.example.demo.Repository.VentaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<VentaEntity> ListarVenta()throws Exception{
        return  ventaRepository.findAll();
    }

    public Optional<VentaEntity> BuscarVentaPorAuto(Integer idauto)throws Exception{
        return  ventaRepository.findByIdauto(new AutoEntity(idauto));
    }


    @Transactional(rollbackOn = Exception.class)
    public void eliminarVenta(Integer id) throws Exception{
        Optional<VentaEntity> optional = ventaRepository.findById(id);
        if (optional.isPresent()){
            ventaRepository.delete(optional.get());
        }
    }

    public Integer ObteneridVenta(Integer idauto){
        VentaEntity ventaEntity = ventaRepository.findByIdauto(new AutoEntity(idauto))
                .orElseThrow(() -> new EntityNotFoundException("Venta no Encontrada :" + idauto));
        return ventaEntity.getId_venta();
    }


}
