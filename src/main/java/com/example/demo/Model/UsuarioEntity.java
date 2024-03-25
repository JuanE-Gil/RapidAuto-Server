package com.example.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;

    @Column(name = "Username")
    private String username;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Apellido_Paterno")
    private String apellido_Paterno;

    @Column(name = "Apellido_Materno")
    private String apellido_Materno;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "contraseña")
    private String contrasena;

    @Column(name = "celular")
    private int celular;

    @Column(name = "pais")
    private String pais;

    @ManyToOne(targetEntity = RolEntity.class)
    @JoinColumn(name = "idRol")
    private RolEntity idRol;

    @Column(name = "Estado")
    private String estado;

    @Lob
    @Column(name = "img", columnDefinition = "LONGBLOB")
    private byte[] img;

    public UsuarioEntity(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
}
