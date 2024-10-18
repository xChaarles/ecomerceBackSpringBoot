package com.carlos.ecom.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orden")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oid;

    @Column(nullable = false, name = "numero_orden", unique = true)
    private String numeroOrden;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Relación muchos a uno con User

    @OneToOne(mappedBy = "orden", cascade = CascadeType.ALL)
    private DetalleOrden detalleOrden;
}
