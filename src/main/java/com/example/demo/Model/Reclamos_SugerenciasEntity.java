package com.example.demo.Model;

import com.example.demo.Model.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "reclamos_sugerencias")
@AllArgsConstructor
@NoArgsConstructor
public class Reclamos_SugerenciasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Idre_su;

    @ManyToOne(targetEntity = UsuarioEntity.class)
    @JoinColumn(name = "IdUsuario")
    private UsuarioEntity usuario;

    @Column(name = "Fecha")
    private String fecha;

    @Column(name ="Mensaje")
    private String mensaje;

    @Lob
    @Column(name = "img", columnDefinition = "LONGBLOB")
    private byte[] img;
}
