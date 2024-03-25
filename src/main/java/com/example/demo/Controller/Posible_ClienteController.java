package com.example.demo.Controller;


import com.example.demo.DTO.Posibles_ClienteDTO;
import com.example.demo.DTO.RespuestaDTO;
import com.example.demo.Model.VentaEntity;
import com.example.demo.Service.Posibles_ClienteServices;
import com.example.demo.Service.UsuarioService;
import com.example.demo.Service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/cliente")
@CrossOrigin
public class Posible_ClienteController {

    @Autowired
    private Posibles_ClienteServices posiblesClienteServices;

    @Autowired
    private UsuarioService usuarioService;



    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> listar()throws Exception{
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", posiblesClienteServices.listar());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping("/listar_por_usuario")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<RespuestaDTO> listarporVenta(Principal principal)throws Exception{
        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", posiblesClienteServices.ListarPorUsuario(userId));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/contactar_usuario")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<RespuestaDTO> contactarVendedor(@ModelAttribute Posibles_ClienteDTO posiblesClienteDTO, Principal principal, @RequestParam int id)throws Exception{
        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
        RespuestaDTO respuesta = new RespuestaDTO("OK","Registrado Correctamente", posiblesClienteServices.registrar(posiblesClienteDTO,id,userId));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);

    }


}
