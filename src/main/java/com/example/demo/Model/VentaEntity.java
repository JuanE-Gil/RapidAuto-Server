package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "venta")
@AllArgsConstructor
@NoArgsConstructor
public class VentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_venta;

    @OneToOne(targetEntity = AutoEntity.class,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_auto")
    private AutoEntity idauto;

    @OneToOne(targetEntity = UsuarioEntity.class)
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity idusuario;

    @Column(name = "fecha_creacion")
    private Date fecha_creacion;

    @Column(name = "precio_auto")
    private double precio_auto;

    @Column(name = "fecha_finalizacion")
    private Date fecha_finalizacion;

    @Column(name = "estado")
    @Size(max = 50)
    private String estado;

    public VentaEntity(Integer id_venta) {
        this.id_venta = id_venta;
    }
}
