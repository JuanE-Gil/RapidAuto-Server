package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;

import java.lang.annotation.Target;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auto")
public class AutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAuto;

    @Column(name = "Modelo")
    private String modelo;

    @Column(name = "Motor")
    private String motor;

    @Column(name = "Kilometraje")
    private String kilometraje;

    @Column(name = "Estado")
    private String estado;

    @Column(name = "Marca")
    private String marca;

    @Column(name = "Pais")
    private String pais;

    @Column(name = "Descripcion")
    private String descripcion;

    @Lob
    @Column(name = "img1", columnDefinition = "LONGBLOB")
    private byte[] img1;

    @Lob
    @Column(name = "img2", columnDefinition = "LONGBLOB")
    private byte[] img2;

    @Lob
    @Column(name = "img3", columnDefinition = "LONGBLOB")
    private byte[] img3;

    @Lob
    @Column(name = "img4", columnDefinition = "LONGBLOB")
    private byte[] img4;

    @Column(name = "precio")
    private double precio;

    @Column(name = "estatus")
    private boolean estatus;


    @ManyToOne(targetEntity = UsuarioEntity.class)
    @JoinColumn(name = "idusuario")
    private UsuarioEntity idusuario;

    @ManyToOne(targetEntity = CategoriaEntity.class)
    @JoinColumn(name = "IdCategoria")
    private CategoriaEntity idCategoria;

    public AutoEntity(Integer idAuto) {
        idAuto = idAuto;
    }
}
