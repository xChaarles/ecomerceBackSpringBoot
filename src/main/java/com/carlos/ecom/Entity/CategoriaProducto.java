package com.carlos.ecom.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table( name = "categoria_producto")
public class CategoriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @Column(nullable = false, name = "nombre")
    private String cnombre;

}
