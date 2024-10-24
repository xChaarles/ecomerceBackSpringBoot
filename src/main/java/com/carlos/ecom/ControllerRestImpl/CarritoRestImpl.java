package com.carlos.ecom.ControllerRestImpl;

import com.carlos.ecom.ControllerRest.CarritoRest;
import com.carlos.ecom.dto.CarritoRequest;
import com.carlos.ecom.dto.Params;
import com.carlos.ecom.serviceImpl.CarritoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CarritoRestImpl implements CarritoRest {

    @Autowired
    CarritoServiceImpl carritoService;

    @Override
    public ResponseEntity<CarritoRequest> addCarrito(@PathVariable Integer userId, @RequestBody Params request){
        return ResponseEntity.ok(carritoService.addCarrito(request.getProductoId(),request.getCantidad(), userId));
    }

    @Override
    public ResponseEntity<CarritoRequest> getCarritoPorUser(@PathVariable Integer userId){
        return ResponseEntity.ok(carritoService.getCarritoPorUser(userId));
    }

    @Override
    public ResponseEntity<CarritoRequest> deleteProductoCarrito(@PathVariable Integer userId, @RequestParam Integer detalleOrdenId){
        return ResponseEntity.ok(carritoService.deleteProductoCarrito(userId, detalleOrdenId));
    }
}
