package com.carlos.ecom.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table( name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "pimg_url")
    private String pimgUrl;

    @Column(nullable = false, name = "pnombre")
    private String pnombre;

    @Column(nullable = false, name = "pdescripcion")
    private String pdescripcion;

    @Column(nullable = false, name = "precio")
    private Integer precio;

    @Column(nullable = false, name = "cantidad")
    private Long cantidad;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaProducto categoria;

}