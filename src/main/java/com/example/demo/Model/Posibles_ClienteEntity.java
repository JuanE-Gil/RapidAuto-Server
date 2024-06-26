package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posibles_cliente")
public class Posibles_ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cliente;

    @ManyToOne(targetEntity = UsuarioEntity.class)
    @JoinColumn(name = "usario")
    private UsuarioEntity usuario;

    @ManyToOne(targetEntity = AutoEntity.class)
    @JoinColumn(name = "auto")
    private AutoEntity auto;

    private String descripcion;

}
