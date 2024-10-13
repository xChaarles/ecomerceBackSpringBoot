package com.carlos.ecom.dto;

import com.carlos.ecom.Entity.CategoriaProducto;
import lombok.Data;

import java.util.List;

@Data
public class CategoriaProductoRes {

    private int statusCode;
    private String error;
    private String message;
    private String cnombre;
    private CategoriaProducto categoriaProducto;
    private List<CategoriaProducto> categoriaProductoList;

}
