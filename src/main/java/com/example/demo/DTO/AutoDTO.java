package com.example.demo.DTO;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutoDTO {


    @NotBlank //Que no sea nulo y que no sea vacio el mensaj
    @Size(min = 1, max = 20)//Maximo de caracteres
    private String modelo;

    @NotBlank
    @Size(min = 1, max = 20)
    private String motor;

    @NotBlank
    @Size(min = 1, max = 15)
    private String kilometraje;

    @NotBlank
    private String estado;

    @NotBlank
    @Size(min = 3, max = 10)
    private String marca;


    private String pais;

    @NotBlank
    private  String descripcion;



    private MultipartFile img1;

    private MultipartFile img2;

    private MultipartFile img3;

    private MultipartFile img4;


    @NotNull
    private double precio;

    private boolean estatus;


    private  Integer idusuario;


    private  int idCategoria;
}
