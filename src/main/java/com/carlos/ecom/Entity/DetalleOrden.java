package com.carlos.ecom.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "detalle_orden")
public class DetalleOrden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "nombre")
    private String nombre;

    @OneToOne
    @JoinColumn(name = "orden_id", nullable = false)
    private Orden orden;  // Relación 1 a 1 con Orden

    @OneToMany(mappedBy = "detalleOrden", cascade = CascadeType.ALL)
    private List<Producto> productos;  // Relación 1 a muchos con Producto
}
