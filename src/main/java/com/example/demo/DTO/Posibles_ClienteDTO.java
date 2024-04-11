package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posibles_ClienteDTO {

    private int usuario;

    private int auto;


    @NotBlank
    @NotNull
    private String descripcion;
}
