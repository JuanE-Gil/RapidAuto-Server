package com.example.demo.Controller;

import com.example.demo.DTO.Reclamos_SugerenciasDTO;
import com.example.demo.DTO.RespuestaDTO;
import com.example.demo.Service.Reclamos_SugerenciasService;
import com.example.demo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/reclamos_sugerencias")
public class Reclamos_SugerenciasController {

    @Autowired
    private Reclamos_SugerenciasService reclamosSugerenciasService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> Listar()throws Exception{
        RespuestaDTO respuestaDTO = new RespuestaDTO("Ok","Listado con Exito",reclamosSugerenciasService.Listar());
        return ResponseEntity.ok(respuestaDTO);
    }

    @PostMapping("/registrar_reclamo_sugerencia")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<RespuestaDTO> registrar(@ModelAttribute  Reclamos_SugerenciasDTO reclamosSugerenciasDTO, Principal principal)throws Exception{
        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
        RespuestaDTO respuestaDTO = new RespuestaDTO("Ok", "Registrado con exito",reclamosSugerenciasService.registrar(reclamosSugerenciasDTO,userId));
        return ResponseEntity.ok(respuestaDTO);
    }

}
