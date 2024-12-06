package com.carlos.ecom.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String img_url;
    private String ciudad;
    private Long numeroContacto;
    private String role;

    public UserDto(Integer id, String nombre, String apellido, String email, String img_url, String ciudad, Long numeroContacto, String role) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.img_url = img_url;
        this.ciudad = ciudad;
        this.numeroContacto = numeroContacto;
        this.role = role;
    }
}
