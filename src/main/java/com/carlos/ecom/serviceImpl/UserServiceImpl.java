package com.carlos.ecom.serviceImpl;

import com.carlos.ecom.Entity.User;
import com.carlos.ecom.Jwt.JwtUtils;
import com.carlos.ecom.dao.UserDao;
import com.carlos.ecom.dto.UserRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserRes singUp(UserRes registrationRequest){
        UserRes userRes = new UserRes();
        try {
            User user = new User();
            user.setNombre(registrationRequest.getNombre());
            user.setApellido(registrationRequest.getApellido());
            user.setEmail(registrationRequest.getEmail());
            user.setImg_url(registrationRequest.getImg_url());
            user.setCiudad(registrationRequest.getCiudad());
            user.setNumeroContacto(registrationRequest.getNumeroContacto());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());
            User UserResult = userDao.save(user);

            if (UserResult.getId()>0){
                userRes.setUser(UserResult);
                userRes.setMessage("Usuario creado Exitosamente");
                userRes.setStatusCode(200);
            }
        }catch (Exception e){
            userRes.setStatusCode(500);
            userRes.setError(e.getMessage());
        }
        return userRes;
    }

    public UserRes login(UserRes loginRes){
        UserRes response = new UserRes();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRes.getEmail(), loginRes.getPassword()));

            var user = userDao.findByEmail(loginRes.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateSecretToken(user);
            var refreshtoken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setImg_url(user.getImg_url());
            response.setRefreshToken(refreshtoken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Inicio de sesion Exitosa");

        }catch (RuntimeException e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public UserRes getAllUser (){
        UserRes userRes = new UserRes();
        try {
            List<User> result = userDao.findAll();
            if (!result.isEmpty()){
                userRes.setUserList(result);
                userRes.setStatusCode(200);
                userRes.setMessage("Exitosa");
            } else {
                userRes.setStatusCode(404);
                userRes.setMessage("No se econtro ningun Producto");
            }
            return userRes;
        }catch (Exception e){
            userRes.setStatusCode(500);
            userRes.setMessage("Ocurrio un ERROR:"+ e.getMessage());
            return userRes;
        }
    }

    public UserRes getUserById(Integer Id){
        UserRes userRes = new UserRes();
        try{
            User userById = userDao.findById(Id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            userRes.setUser(userById);
            userRes.setStatusCode(200);
            userRes.setMessage("Usuario con id '"  + Id + "' encontrada Exitosamente");
        }catch (Exception e){
            userRes.setStatusCode(500);
            userRes.setMessage("Ocurrio un ERROR"+ e.getMessage());
        }
        return userRes;
    }



}
