package com.example.demo.DTO;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario_ActualizarDTO {
    

    private String Username;


    @NotBlank
    @Size(min = 3, max = 8)
    private String nombre;

    @NotBlank
    @Size(min = 3, max = 20)
    private String apellido_Paterno;

    @NotBlank
    @Size(min = 3, max = 20)
    private String apellido_Materno;

    @NotBlank
    @Email
    private String correo_electronico;

    @NotBlank
    private String contrasena;

    @NotNull(message = "El celular no puede estar vacio")
    private int celular;


    private String pais;

    private int idrol;

    private MultipartFile img;

    private String estado;
}
