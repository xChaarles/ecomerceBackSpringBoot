package com.carlos.ecom.ControllerRest;

import com.carlos.ecom.dto.CarritoRequest;
import com.carlos.ecom.dto.Params;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping
public interface CarritoRest {

    @PostMapping("/user/carrito/{userId}")
    public ResponseEntity<CarritoRequest> addCarrito(@PathVariable Integer userId, @RequestBody Params request);

    @GetMapping("/user/get-carrito/{userId}")
    public ResponseEntity<CarritoRequest> getCarritoPorUser(@PathVariable Integer userId);

    @DeleteMapping("/user/delete-carrito/{userId}")
    public ResponseEntity<CarritoRequest> deleteProductoCarrito(@PathVariable Integer userId, @RequestParam Integer detalleOrdenId);

}
