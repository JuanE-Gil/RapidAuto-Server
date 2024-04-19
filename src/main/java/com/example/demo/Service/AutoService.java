package com.example.demo.Service;

import com.example.demo.DTO.AutoDTO;
import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.CategoriaEntity;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.AutoRepository;
import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Service.Compresor.ImageCompressor;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImageCompressor imageCompressor;

    @Autowired
    private VentaService ventaService;

    private static final boolean ESTATUS = true;


    public Optional<AutoEntity> ListarUnSoloAuto(Integer id)throws Exception{
        return  autoRepository.findById(id);
    }

    public List<AutoEntity> ListarPorEstatus() throws Exception {
        return autoRepository.findByEstatus(ESTATUS);
    }

    public List<AutoEntity> ListarPorEstatusYUsuario(UsuarioEntity idusuario) throws Exception {
        return autoRepository.findByIdusuarioAndEstatus(idusuario, ESTATUS);
    }

    public List<AutoEntity> listarauto() throws Exception {
        return autoRepository.findAll();
    }

    public List<AutoEntity> ListarPorEstatusYModelo(String modelo) throws Exception {
        List<AutoEntity> autosPorModelo = autoRepository.findByModeloContainingIgnoreCase(modelo);
        return autosPorModelo.stream()
                .filter(auto -> auto.isEstatus() == true)
                .collect(Collectors.toList());
    }

    public List<AutoEntity> ListarPorEstatusYCategoria(CategoriaEntity idcategoria) throws Exception {
        return autoRepository.findByIdCategoriaAndAndEstatus(idcategoria, ESTATUS); 
    }

    public List<AutoEntity> ListarPorEstatusYCategoria_Description(String descripcion) throws Exception {
       CategoriaEntity categoriaEntity =  categoriaRepository.findByDescripcion(descripcion)
               .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrado"));
       Integer id = categoriaEntity.getIdCategoria();
        return autoRepository.findByIdCategoriaAndAndEstatus(new CategoriaEntity(id),ESTATUS);
    }


    public List<AutoEntity> ListarPorEstatusYMarca(String Marca) throws Exception {
        return autoRepository.findByMarcaAndEstatus(Marca, ESTATUS);
    }

    public List<AutoEntity> ListarPorEstatusYEstado(String Estado) throws Exception {
        return autoRepository.findByEstadoAndEstatus(Estado, ESTATUS);
    }


    public List<AutoEntity> Filtro_Precio(double precio_mayor, double precio_menor) throws Exception {
        List<AutoEntity> result = new ArrayList<>();

        for (AutoEntity auto : ListarPorEstatus()) {
            double precio = auto.getPrecio();

            if (auto.isEstatus() == ESTATUS && precio >= precio_menor && precio <= precio_mayor) {
                result.add(auto);
            }
        }
        return result;
    }





    @Transactional(rollbackOn = Exception.class)
    public AutoEntity registrarAuto(Integer userId, AutoDTO dto) throws Exception {
            AutoEntity autoEntity = new AutoEntity();
            autoEntity.setModelo(dto.getModelo());
            autoEntity.setMotor(dto.getMotor());
            autoEntity.setKilometraje(dto.getKilometraje());
            autoEntity.setEstado(dto.getEstado());
            autoEntity.setMarca(dto.getMarca());
            autoEntity.setPais("Perú");
            autoEntity.setDescripcion(dto.getDescripcion());

            // Asignar la dirección de imagen a todas las propiedades de imagen (img1, img2, img3, img4)
            autoEntity.setImg1(dto.getImg1().getBytes());
            autoEntity.setImg2(dto.getImg2().getBytes());
            autoEntity.setImg3(dto.getImg3().getBytes());
            autoEntity.setImg4(dto.getImg4().getBytes());
            autoEntity.setEstatus(dto.isEstatus());
            autoEntity.setPrecio(dto.getPrecio());
            autoEntity.setIdCategoria(new CategoriaEntity(dto.getIdCategoria()));
            autoEntity.setIdusuario(new UsuarioEntity(userId));

            autoRepository.save(autoEntity);
            return autoEntity;
    }


    @Transactional(rollbackOn = Exception.class)
    public AutoEntity actualizar_auto(Integer userId, AutoDTO dto, Integer idAuto) throws Exception {
        Optional<AutoEntity> optional = autoRepository.findById(idAuto);
        if (optional.isPresent()) {
            AutoEntity autoEntity = optional.get();
            autoEntity.setModelo(dto.getModelo());
            autoEntity.setMotor(dto.getMotor());
            autoEntity.setKilometraje(dto.getKilometraje());
            autoEntity.setEstado(dto.getEstado());
            autoEntity.setMarca(dto.getMarca());
            autoEntity.setPais("Perú");
            autoEntity.setDescripcion(dto.getDescripcion());

            if (dto.getImg1() != null && !dto.getImg1().isEmpty()){
                byte[] img1 = imageCompressor.compressImage(dto.getImg1().getBytes());
                autoEntity.setImg1(img1);
            }else {
                autoEntity.setImg1(optional.get().getImg1());
            }
            if (dto.getImg2() != null && !dto.getImg2().isEmpty()){
                byte[] img2 = imageCompressor.compressImage(dto.getImg2().getBytes());
                autoEntity.setImg2(img2);
            }else {
                autoEntity.setImg2(optional.get().getImg2());
            }
            if (dto.getImg3() != null && !dto.getImg3().isEmpty()){
                byte[] img3 = imageCompressor.compressImage(dto.getImg3().getBytes());
                autoEntity.setImg3(img3);
            }else {
                autoEntity.setImg3(optional.get().getImg3());
            }
            if (dto.getImg4() != null && !dto.getImg4().isEmpty()){
                byte[] img4 = imageCompressor.compressImage(dto.getImg4().getBytes());
                autoEntity.setImg4(img4);
            }else{
                autoEntity.setImg4(optional.get().getImg4());
            }
            autoEntity.setEstatus(dto.isEstatus());
            autoEntity.setPrecio(dto.getPrecio());
            autoEntity.setIdCategoria(new CategoriaEntity(dto.getIdCategoria()));
            autoEntity.setIdusuario(new UsuarioEntity(userId));

            autoRepository.save(autoEntity);
            return autoEntity;
        } else {
            return null;
        }

    }

    @Transactional(rollbackOn = Exception.class)
    public String eliminar_auto_usuario(Integer idAuto) throws Exception {
        boolean estatus = false;
        autoRepository.actualizarEstatus(idAuto,estatus);
        return ":-)";

    }

    @Transactional(rollbackOn = Exception.class)
    public void eliminarauto(Integer id) throws Exception {
        Optional<AutoEntity> optional = autoRepository.findById(id);
        if (optional.isPresent()) {
            autoRepository.delete(optional.get());
        }
    }
    public AutoEntity obtenerAutosUsuario(UsuarioEntity usuario) {
        List<AutoEntity> listaAutos = autoRepository.findByIdusuarioAndEstatus(usuario, true);
        if (!listaAutos.isEmpty()) {
            return listaAutos.get(0);
        } else {
            return null;
        }
    }

}

