package com.example.demo.Controller;


import com.example.demo.DTO.RespuestaDTO;
import com.example.demo.DTO.UsuarioDTO;
import com.example.demo.DTO.Usuario_ActualizarDTO;
import com.example.demo.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/usuario")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


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


    @PostMapping("/registrar_usuario")
    public ResponseEntity<String> registrar_usuario(@Valid  @ModelAttribute UsuarioDTO dto) throws  Exception{
        String mensaje = usuarioService.registrar_usuario(dto);
        if (mensaje.equals("Usuario Registrado Correctamente")) {
            return ResponseEntity.ok(mensaje);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
    }


    @PostMapping("/registrar_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> registrar_admin(@Valid @ModelAttribute UsuarioDTO dto) throws  Exception{
        String mensaje = usuarioService.registrar_admin(dto);
        if (mensaje.equals("Usuario Registrado Correctamente")) {
            return ResponseEntity.ok(mensaje);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
    }

    @PutMapping("/actualizar_usuario")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<String> actualizar(@Valid @ModelAttribute Usuario_ActualizarDTO dto, Principal principal) throws  Exception{
        Integer idusuario = usuarioService.obtenerUserIdDesdePrincipal(principal);
        String mensaje = usuarioService.actualizar(dto,idusuario);
        if (mensaje.equals("Usuario Actualizado Correctamente")) {
            return ResponseEntity.ok(mensaje);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
    }

    @PutMapping("/actualizar_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> actualizar_Admin(@Valid @ModelAttribute Usuario_ActualizarDTO dto, @RequestParam Integer usuario) throws  Exception{
        String mensaje = usuarioService.actualizar_admin(dto,usuario);
        if (mensaje.equals("Usuario Actualizado Correctamente")) {
            return ResponseEntity.ok(mensaje);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
        }
    }

    @DeleteMapping("/eliminar_usuario")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<RespuestaDTO> eliminar(Principal principal) throws  Exception{
        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
        usuarioService.eliminar(userId);
        RespuestaDTO respuesta = new RespuestaDTO("OK","Eliminado Correctamente", ":)");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar_admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> eliminar_admin(@RequestParam Integer id) throws  Exception{
        usuarioService.eliminar(id);
        RespuestaDTO respuesta = new RespuestaDTO("OK","Eliminado Correctamente", ":)");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}