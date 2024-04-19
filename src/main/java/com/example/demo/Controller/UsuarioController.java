package com.example.demo.Controller;


import com.example.demo.DTO.RespuestaDTO;
import com.example.demo.DTO.UsuarioDTO;
import com.example.demo.DTO.Usuario_ActualizarDTO;
import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.AutoRepository;
import com.example.demo.Service.AutoService;
import com.example.demo.Service.UsuarioService;
import com.example.demo.Service.VentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutoService autoService;

    @Autowired
    private VentaService ventaService;

    @Autowired
    private AutoRepository autoRepository;

    @GetMapping("/version")
    public String version()throws Exception{
        String version = "VERSION 5 SPRING BOOT RAKTEAM COMPANY";
        return  version;
    }
    @GetMapping("/obtener_rol")
    public ResponseEntity<RespuestaDTO> obtener_rol(Principal principal) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "El Rol es:", usuarioService.ObtenerRol(principal));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/listar_solo_usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> listar_solo_usuarios() throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", usuarioService.listar_solo_usuarios());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/listar_solo_admins")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> listar_solo_admins() throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", usuarioService.listar_solo_admins());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> ListarUsuario() throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", usuarioService.listar());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/buscar_usuario")
    @PreAuthorize("hasAnyRole('ADMIN','USUARIO')")
    public ResponseEntity<RespuestaDTO> BuscarUsuario(Principal principal) throws Exception {
        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Encontrado con Éxito", usuarioService.Buscar(userId));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/buscar_usuario_por_id")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> Buscar_Usuario_por_id(@RequestParam Integer id) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Encontrado con Éxito", usuarioService.Buscar(id));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @PostMapping("/registrar_usuario")
    public ResponseEntity<?> registrar_usuario(@Valid  @ModelAttribute UsuarioDTO dto) throws  Exception {
       return new ResponseEntity<>(usuarioService.registrar_usuario(dto),HttpStatus.CREATED);
    }
    @PostMapping("/registrar_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar_admin(@Valid @ModelAttribute UsuarioDTO dto) throws  Exception{
        return new ResponseEntity<>(usuarioService.registrar_admin(dto),HttpStatus.CREATED);
    }

    @PutMapping("/actualizar_usuario")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<?> actualizar(@Valid @ModelAttribute Usuario_ActualizarDTO dto, Principal principal) throws  Exception{
        Integer idusuario = usuarioService.obtenerUserIdDesdePrincipal(principal);
        return new ResponseEntity<>(usuarioService.actualizar_usuario(dto,idusuario),HttpStatus.OK);
    }

    @PutMapping("/actualizar_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> actualizar_Admin(@Valid @ModelAttribute Usuario_ActualizarDTO dto, @RequestParam Integer usuario) throws  Exception{
        return new ResponseEntity<>(usuarioService.actualizar_admin(dto,usuario),HttpStatus.OK);
    }

//    @DeleteMapping("/eliminar_usuario")
//    @PreAuthorize("hasRole('USUARIO')")
//    public ResponseEntity<RespuestaDTO> eliminar(Principal principal) throws  Exception{
//        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
//        usuarioService.eliminar(userId);
//        RespuestaDTO respuesta = new RespuestaDTO("OK","Eliminado Correctamente", ":)");
//        return new ResponseEntity<>(respuesta, HttpStatus.OK);
//    }

    @DeleteMapping("/eliminar_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> eliminar_admin(@RequestParam Integer id) throws  Exception{
        usuarioService.eliminar(id);
        RespuestaDTO respuesta = new RespuestaDTO("OK","Eliminado Correctamente", ":)");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/desactivado_por_usuario")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<String> desactivar_por_usuario(Principal principal)throws Exception{
        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
        usuarioService.desactivarUsuario(userId);
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId_usuario(userId);
        List<AutoEntity> autos = autoRepository.findByIdusuarioAndEstatus(usuario,true);
        for (AutoEntity auto : autos) {
            autoService.eliminar_auto_usuario(auto.getIdAuto());
            Integer id_venta = ventaService.ObteneridVenta(auto.getIdAuto());
            ventaService.insertEstado(id_venta, "Usuario Desactivado");
        }
        return ResponseEntity.ok("USUARIO DESACTIVADO CON EXITO");

    }

    @PutMapping("/desactivado_por_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> desactivar_por_admin(@RequestParam Integer id)throws Exception{
        Integer userId = usuarioService.Id_usuario(id);
        usuarioService.desactivarUsuario(userId);
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId_usuario(userId);
        List<AutoEntity> autos = autoRepository.findByIdusuarioAndEstatus(usuario,true);
        for (AutoEntity auto : autos) {
            autoService.eliminar_auto_usuario(auto.getIdAuto());
            Integer id_venta = ventaService.ObteneridVenta(auto.getIdAuto());
            ventaService.insertEstado(id_venta, "Usuario Desactivado por Administrado");
        }
        return ResponseEntity.ok("USUARIO DESACTIVADO CON EXITO");

    }



}
