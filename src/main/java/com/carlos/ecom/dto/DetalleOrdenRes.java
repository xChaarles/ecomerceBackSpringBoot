package com.carlos.ecom.dto;

import com.carlos.ecom.Entity.Producto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DetalleOrdenRes {

    private int statusCode;
    private String error;
    private String message;
    private Integer id;
    private String nombre;
    private String imgp;
    private Long cantidad;
    private Integer precio;
    private double total;
    private String Ordenid;
    private ProductoRes productoRes;
    private double totalAcumulado;

}
