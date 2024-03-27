package com.example.demo.Service;


import com.example.demo.DTO.UsuarioDTO;
import com.example.demo.DTO.Usuario_ActualizarDTO;
import com.example.demo.Model.RolEntity;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolService rolService;


    @Autowired
    private ImageCompressor imageCompressor;


    public List<UsuarioEntity> listar() throws Exception{
        return usuarioRepository.findAll();
    }

    public Optional<UsuarioEntity> Buscar(Integer Id) throws Exception{
        return usuarioRepository.findById(Id);
    }

    public String ObtenerRol(Principal principal)throws Exception{
        String username = principal.getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return usuario.getIdRol().getDescripcion();
    }
    public List<UsuarioEntity> listar_solo_usuarios()throws Exception{
        String rol = "USUARIO";
        Integer idrol = rolService.buscarRol(rol);
        return  usuarioRepository.findByIdRol(new RolEntity(idrol));
    }
    public List<UsuarioEntity> listar_solo_admins()throws Exception{
        String rol = "ADMIN";
        Integer idrol = rolService.buscarRol(rol);
        return  usuarioRepository.findByIdRol(new RolEntity(idrol));
    }
    @Transactional(rollbackOn = Exception.class )
    public String registrar_admin(UsuarioDTO dto)throws Exception{
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity = registrar(dto);
        usuarioEntity.setIdRol(new RolEntity(dto.getIdrol()));
        usuarioRepository.save(usuarioEntity);
        return "Usuario Registrado Correctamente";
    }

    public UsuarioEntity registrar(UsuarioDTO dto)throws Exception{
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        String username = dto.getUsername();
        String correo_electronico = dto.getCorreo_electronico();
        Optional<UsuarioEntity> usuario = (usuarioRepository.findByUsername(username));
        Optional <UsuarioEntity>  correo = (usuarioRepository.findByCorreoElectronico(correo_electronico));
        if (usuario.isPresent()){
            throw new RegistroExistenteException();
        }else {
            usuarioEntity.setUsername(dto.getUsername());
        }
        usuarioEntity.setNombre(dto.getNombre());
        usuarioEntity.setApellido_Paterno(dto.getApellido_Paterno());
        usuarioEntity.setApellido_Materno(dto.getApellido_Materno());
        if (correo.isPresent()){
            throw new RegistroExistenteException("Correo electrónico en uso");
        }else {
            usuarioEntity.setCorreoElectronico(dto.getCorreo_electronico());
        }
        usuarioEntity.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        usuarioEntity.setCelular(dto.getCelular());
        usuarioEntity.setPais("Perú");
        usuarioEntity.setEstado("Activo");
        if (dto.getImg() != null) {

            byte[] img = imageCompressor.compressImage(dto.getImg().getBytes());
            usuarioEntity.setImg(img);

        } else {
            usuarioEntity.setImg(null);
        }
        return usuarioEntity;
    }

    @Transactional(rollbackOn = Exception.class )
    public String registrar_usuario(UsuarioDTO dto)throws Exception{
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity = registrar(dto);
        usuarioEntity.setIdRol(new RolEntity(3));
        usuarioRepository.save(usuarioEntity);
        return "Usuario Registrado Correctamente";
    }

    @Transactional(rollbackOn =  Exception.class)
    public String actualizar_usuario(Usuario_ActualizarDTO dto, Integer idusuario)throws Exception{
        Optional<UsuarioEntity> optional = usuarioRepository.findById(idusuario);
        if (optional.isPresent()){
            UsuarioEntity usuarioEntity = optional.get();
            usuarioEntity = actualizar(dto,idusuario);
            usuarioEntity.setEstado("Activo");
            usuarioEntity.setIdRol(new RolEntity(3));
            usuarioRepository.save(usuarioEntity);
            return "Usuario Actualizado Correctamente";

        }else {
            return "Usuario no encontrado";
        }
    }


    public UsuarioEntity actualizar(Usuario_ActualizarDTO dto,Integer idusuario)throws Exception{
        Optional<UsuarioEntity> optional = usuarioRepository.findById(idusuario);
        if (optional.isPresent()){
            UsuarioEntity usuarioEntity = optional.get();
            usuarioEntity.setNombre(dto.getNombre());
            usuarioEntity.setApellido_Paterno(dto.getApellido_Paterno());
            usuarioEntity.setApellido_Materno(dto.getApellido_Materno());
            if (dto.getCorreo_electronico().equals(usuarioEntity.getCorreoElectronico()) || !Correo_si_esta_presente(dto.getCorreo_electronico())) {
                usuarioEntity.setCorreoElectronico(dto.getCorreo_electronico());
            } else {
                throw new RegistroExistenteException("Correo electrónico en uso");
            }
            usuarioEntity.setContrasena(passwordEncoder.encode(dto.getContrasena()));
            usuarioEntity.setCelular(dto.getCelular());
            usuarioEntity.setPais("Perú");
            byte[] img = imageCompressor.compressImage(dto.getImg().getBytes());
            usuarioEntity.setImg(img);
            return usuarioEntity;
        }else {
            return null;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public String actualizar_admin(Usuario_ActualizarDTO dto, Integer idusuario) throws Exception {
        Optional<UsuarioEntity> optional = usuarioRepository.findById(idusuario);
        if (optional.isPresent()) {
            UsuarioEntity usuarioEntity = optional.get();
            usuarioEntity = actualizar(dto,idusuario);
            usuarioEntity.setEstado(dto.getEstado());
            usuarioEntity.setIdRol(new RolEntity(dto.getIdrol()));

            usuarioRepository.save(usuarioEntity);
            return "Usuario Actualizado Correctamente";
        } else {
            return "Usuario no encontrado";
        }
    }
    @Transactional(rollbackOn = Exception.class)
    public void eliminar(Integer id) throws Exception{
        Optional<UsuarioEntity> optional = usuarioRepository.findById(id);
        if (optional.isPresent()){
            usuarioRepository.delete(optional.get());
        }
    }

    //La interface "Principal" es una interface predefinida de spring security donde encapsula y almacena la informacion
    //del usuario autenticado
    public Integer obtenerUserIdDesdePrincipal(Principal principal) {
        String username = principal.getName();
        UsuarioEntity usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
        return usuario.getId_usuario();
    }

    public boolean Correo_si_esta_presente(String correo){
        Optional <UsuarioEntity>  correo_en_uso = (usuarioRepository.findByCorreoElectronico(correo));
        if(correo_en_uso.isPresent()){
            return true;
        }else {
            return false;
        }
    }



}
