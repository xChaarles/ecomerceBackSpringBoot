package com.carlos.ecom.ControllerRestImpl;

import com.carlos.ecom.ControllerRest.UserControllerRest;
import com.carlos.ecom.dto.UserRes;
import com.carlos.ecom.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
