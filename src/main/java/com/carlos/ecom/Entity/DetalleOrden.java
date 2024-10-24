package com.carlos.ecom.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "detalle_orden")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "imgp")
    private String imgp;

    @Column(name = "cantidad")
    private Long cantidad;

    @Column(name = "precio")
    private Integer precio;

    @Column(name = "total")
    private double total;

    @ManyToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    @ManyToOne
    private Orden orden;

    @OneToOne
    private Producto producto;
}
