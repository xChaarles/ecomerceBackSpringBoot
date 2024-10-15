package com.carlos.ecom.ControllerRest;

import com.carlos.ecom.Entity.User;
import com.carlos.ecom.dto.UserRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
public interface UserControllerRest {

    @PostMapping("/auth/singUp")
    public ResponseEntity<UserRes> singUp(@RequestBody UserRes reg);

    @PostMapping("/auth/login")
    public ResponseEntity<UserRes> login(@RequestBody UserRes reg);

    @GetMapping("/admin/all-user")
    public ResponseEntity<UserRes> getAllUser();

    @GetMapping("/admin/user/{Id}")
    public ResponseEntity<UserRes> getUserById(@PathVariable Integer Id);

    @PutMapping("/admin/update-user/{userId}")
    public ResponseEntity<UserRes> updateUser(@PathVariable Integer userId, @RequestBody User reg);

    @DeleteMapping("/admin/delete-user/{userId}")
    public ResponseEntity<UserRes> deleteUser(@PathVariable Integer userId);

}
