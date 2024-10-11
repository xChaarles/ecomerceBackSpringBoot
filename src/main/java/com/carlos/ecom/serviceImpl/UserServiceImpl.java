package com.carlos.ecom.serviceImpl;

import com.carlos.ecom.Entity.User;
import com.carlos.ecom.Jwt.JwtUtils;
import com.carlos.ecom.dao.UserDao;
import com.carlos.ecom.dto.UserRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

}
