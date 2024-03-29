package com.example.demo.Controller;

import com.example.demo.DTO.RespuestaDTO;
import com.example.demo.Model.AutoEntity;
import com.example.demo.Service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> ListarVenta() throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", ventaService.ListarVenta());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @GetMapping("/busqueda")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> BuscarVenta(@RequestParam Integer id) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Encontrado con Éxito", ventaService.BuscarVentaPorAuto(id));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @GetMapping("/Busqueda_de_venta_idauto")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> BuscarVenta_Auto(@RequestParam Integer id) throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Encontrado con Éxito", ventaService.ObteneridVenta(id));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> eliminar(@RequestParam Integer id) throws Exception {
        ventaService.eliminarVenta(id);
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Eliminado con Exito",":)");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
