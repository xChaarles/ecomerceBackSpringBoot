package com.carlos.ecom.ControllerRestImpl;

import com.carlos.ecom.ControllerRest.UserControllerRest;
import com.carlos.ecom.Entity.User;
import com.carlos.ecom.dto.UserRes;
import com.carlos.ecom.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControlerRestImpl implements UserControllerRest {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Override
    public ResponseEntity<UserRes> singUp(@RequestBody UserRes reg){
        return ResponseEntity.ok(userServiceImpl.singUp(reg));
    }

    @Override
    public ResponseEntity<UserRes> login(@RequestBody UserRes reg){
        return  ResponseEntity.ok(userServiceImpl.login(reg));
    }

    @Override
    public ResponseEntity<UserRes> getAllUser(){
        return ResponseEntity.ok(userServiceImpl.getAllUser());
    }

    @Override
    public ResponseEntity<UserRes> getUserById(@PathVariable Integer Id){
        return ResponseEntity.ok(userServiceImpl.getUserById(Id));
    }
}
