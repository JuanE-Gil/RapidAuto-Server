package com.example.demo.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reclamos_SugerenciasDTO {


    private Integer usuario;


    private String fecha;


    private String mensaje;


    private MultipartFile img;
}
