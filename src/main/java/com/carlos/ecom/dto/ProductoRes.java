package com.carlos.ecom.dto;

import com.carlos.ecom.Entity.Producto;
import lombok.Data;

import java.util.List;

@Data
public class ProductoRes {

    private int statusCode;
    private String error;
    private String message;
    private String pimgUrl;
    private String pnombre;
    private String pdescripcion;
    private Integer precio;
    private Long cantidad;
    private String categoria;
    private Producto producto;
    private List<Producto> productoList;

}
