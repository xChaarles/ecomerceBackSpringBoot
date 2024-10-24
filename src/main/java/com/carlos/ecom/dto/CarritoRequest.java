package com.carlos.ecom.dto;

import com.carlos.ecom.Entity.DetalleOrden;
import com.carlos.ecom.Entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarritoRequest {

    private int statusCode;
    private String error;
    private String message;
    private Integer id;
    private double totalAcumulado;
    private UserCarrito userDTO;
    private List<DetalleOrdenRes> detalles;

}
