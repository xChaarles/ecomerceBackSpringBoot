package com.carlos.ecom.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCarrito {

    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String img_url;
    private String ciudad;
    private Long numeroContacto;

}
