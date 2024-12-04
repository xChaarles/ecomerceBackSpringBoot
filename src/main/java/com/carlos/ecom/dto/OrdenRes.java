package com.carlos.ecom.dto;

import com.carlos.ecom.Entity.Orden;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdenRes {

    private int statusCode;
    private String error;
    private String message;
    private Integer oid;
    private String numeroOrden;
    private String estado;
    private LocalDateTime fechaCreacion;
    private UserRes user;
    private DetalleOrdenRes detalleOrden;
    private List<DetalleOrdenRes> detalleOrdenList;
    private List<Orden> ordenList;


}


