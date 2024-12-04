package com.carlos.ecom.Entity;

import aj.org.objectweb.asm.commons.InstructionAdapter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orden")
public class Orden {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer oid;

    @Column(name = "numero_orden", unique = true)
    private String numeroOrden;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaRecibido;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleOrden> detalleOrdens;
}
