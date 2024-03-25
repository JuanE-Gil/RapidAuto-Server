package com.example.demo.Controller;
import com.example.demo.DTO.CategoriaDTO;
import com.example.demo.DTO.RespuestaDTO;
import com.example.demo.Service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria")
@CrossOrigin
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping
    public ResponseEntity<RespuestaDTO> ListarCategoria() throws Exception {
        RespuestaDTO respuesta = new RespuestaDTO("OK", "Listado con Éxito", categoriaService.listar_categoria());
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @PostMapping("/registrar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> registrar(@Valid  @ModelAttribute CategoriaDTO dto) throws  Exception{
        RespuestaDTO respuesta = new RespuestaDTO("OK","Registrado Correctamente", categoriaService.registrar_categoria(dto));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }


    @PutMapping("/actualizar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> actualizar(@Valid @ModelAttribute CategoriaDTO dto,@RequestParam Integer id_categoria) throws  Exception{
        RespuestaDTO respuesta = new RespuestaDTO("OK","Actualizado Correctamente", categoriaService.actualizar_categoria(dto,id_categoria));
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    @DeleteMapping("/eliminar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RespuestaDTO> eliminar(@RequestParam  Integer id_categoria) throws  Exception{
        categoriaService.eliminar(id_categoria);
        RespuestaDTO respuesta = new RespuestaDTO("OK","Elimnado Correctamente",":)");
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
}
