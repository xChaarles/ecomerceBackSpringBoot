package com.carlos.ecom.ControllerRest;

import com.carlos.ecom.dto.UserRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public interface UserControllerRest {

    @PostMapping("/auth/singUp")
    public ResponseEntity<UserRes> singUp(@RequestBody UserRes reg);
}
