package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Posibles_ClienteDTO {

    private int usuario;

    private int auto;


    private String descripcion;
}
