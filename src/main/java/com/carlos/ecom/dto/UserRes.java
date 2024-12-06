package com.carlos.ecom.dto;

import com.carlos.ecom.Entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String img_url;
    private String ciudad;
    private Long numeroContacto;
    private String password;
    private String role;
    private User user;
    private List<User> userList;
    private List<UserDto> userResList;

}
