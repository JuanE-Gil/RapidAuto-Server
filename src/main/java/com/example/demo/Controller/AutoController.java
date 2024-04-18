package com.example.demo.Controller;

import com.example.demo.DTO.AutoDTO;
import com.example.demo.DTO.RespuestaDTO;
import com.example.demo.Model.AutoEntity;
import com.example.demo.Model.CategoriaEntity;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Service.AutoService;
import com.example.demo.Service.UsuarioService;
import com.example.demo.Service.VentaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auto")
@CrossOrigin
@Slf4j
public class AutoController {

    @Autowired
    private AutoService autoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<RespuestaDTO> ListarAuto() throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", autoService.listarauto());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/listar-un-Auto")
    public ResponseEntity<RespuestaDTO> ListarUnSoloAuto(@RequestParam Integer id)throws Exception{
        RespuestaDTO respuesta = new RespuestaDTO("Ok","Listado con Exito",autoService.ListarUnSoloAuto(id));
        return  new ResponseEntity<>(respuesta,HttpStatus.OK);

    }

    @GetMapping("/filtro/disponible")
    public ResponseEntity<RespuestaDTO> ListarAutoEstatus() throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", autoService.ListarPorEstatus());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @GetMapping("/filtro/precio")
    public ResponseEntity<RespuestaDTO> ListarAutoPrecio(@RequestParam double preciomayor, @RequestParam double preciomenor) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", autoService.Filtro_Precio(preciomayor,preciomenor));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/filtro/marca")
    public ResponseEntity<RespuestaDTO> ListarAutoMarca(@RequestParam String marca) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", autoService.ListarPorEstatusYMarca(marca));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/filtro/estado")
    public ResponseEntity<RespuestaDTO> ListarAutoEstado(@RequestParam String estado) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", autoService.ListarPorEstatusYEstado(estado));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping("/busqueda/modelo")
    public ResponseEntity<RespuestaDTO> ListarAutoEstatusYModelo(@RequestParam String modelo ) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Encontrado con Éxito", autoService.ListarPorEstatusYModelo(modelo));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/busqueda/categoria")
    public ResponseEntity<RespuestaDTO> ListarAutoEstatusYCategoria(@RequestParam CategoriaEntity id) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Encontrado con Éxito", autoService.ListarPorEstatusYCategoria(id));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/busqueda/categoria_description")
    public ResponseEntity<RespuestaDTO> ListarAutoEstatusYCategoria_Description(@RequestParam String description) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Encontrado con Éxito", autoService.ListarPorEstatusYCategoria_Description(description));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("listarAuto/usuario")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<RespuestaDTO> ListarAutoEstatusYUsuario(Principal principal) throws Exception {
        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId_usuario(userId);
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Encontrado con Éxito", autoService.ListarPorEstatusYUsuario(usuarioEntity));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasAnyRole('USUARIO')")
    public ResponseEntity<RespuestaDTO> registrar(@Valid  @ModelAttribute AutoDTO dto, Principal principal) throws  Exception{
        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
        RespuestaDTO respuesta = new RespuestaDTO("OK","Registrado Correctamente", autoService.registrarAuto(userId,dto));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/actualizar")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<RespuestaDTO> actualizar(@Valid @ModelAttribute AutoDTO dto, Principal principal,@RequestParam Integer id) throws  Exception{
        Integer userId = usuarioService.obtenerUserIdDesdePrincipal(principal);
        RespuestaDTO respuesta = new RespuestaDTO("OK","Actualizado Correctamente", autoService.actualizar_auto(userId,dto,id));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @PutMapping("/eliminar/auto_usuario")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<RespuestaDTO> eliminar_auto(@RequestParam Integer id) throws  Exception{
        RespuestaDTO respuesta = new RespuestaDTO("OK"," Eliminado Correctamente", autoService.eliminar_auto_usuario(id));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> eliminar(@RequestParam Integer Id) throws  Exception{
        Integer id_venta = ventaService.ObteneridVenta(Id);
        ventaService.eliminarVenta(id_venta);
        log.info("MENSAJE DE VENTA");
        log.info("id venta:" + id_venta);
        autoService.eliminarauto(Id);
        RespuestaDTO respuesta = new RespuestaDTO("OK","Eliminado Correctamente", ":)");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PutMapping("/vendido")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<RespuestaDTO> vendido(@RequestParam Integer Id, @RequestParam String estado) throws  Exception{
        Integer id_venta = ventaService.ObteneridVenta(Id);
        ventaService.insertEstado(id_venta,estado);
        log.info("MENSAJE DE VENTA");
        log.info("id venta:" + id_venta);
        autoService.eliminar_auto_usuario(Id);
        RespuestaDTO respuesta = new RespuestaDTO("OK","AUTO VENDIDO CORRECTAMENTE", ":)");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
