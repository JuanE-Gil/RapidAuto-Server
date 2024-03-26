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

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,20}$", message = "La contraseña debe tener de 4 a 8 caracteres y debe contener números, letras minúsculas y mayúsculas.")
    private String contrasena;

    @NotNull(message = "El celular no puede estar vacio")
    private int celular;


    private String pais;

    private int idrol;

    private MultipartFile img;

    private String estado;
}
