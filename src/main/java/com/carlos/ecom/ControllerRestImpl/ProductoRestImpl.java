package com.carlos.ecom.ControllerRestImpl;

import com.carlos.ecom.ControllerRest.ProductoRest;
import com.carlos.ecom.Entity.Producto;
import com.carlos.ecom.dto.ProductoRes;
import com.carlos.ecom.serviceImpl.ProdcutoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductoRestImpl implements ProductoRest {

    @Autowired
    ProdcutoServiceImpl prodcutoServiceImpl;

    @Override
    public ResponseEntity<ProductoRes> getAllProducto(){
        return ResponseEntity.ok(prodcutoServiceImpl.getAllProdcuto());
    }

    @Override
    public ResponseEntity<ProductoRes> getProductoById(@PathVariable Integer Pid){
        return ResponseEntity.ok(prodcutoServiceImpl.getProductoById(Pid));
    }

    @Override
    public ResponseEntity<ProductoRes> createProducto(@RequestBody ProductoRes reg){
        return ResponseEntity.ok(prodcutoServiceImpl.createProducto(reg));
    }

    @Override
    public ResponseEntity<ProductoRes> updateProducto(@PathVariable Integer productoId, @RequestBody ProductoRes reg){
        return ResponseEntity.ok(prodcutoServiceImpl.updateProducto(productoId, reg));
    }

    @Override
    public  ResponseEntity<ProductoRes> deleteProducto(@PathVariable Integer productoId){
        return ResponseEntity.ok(prodcutoServiceImpl.deleteProducto(productoId));
    }

    @Override
    public ResponseEntity<ProductoRes> obtenerProductoPorNombreCategoria(@PathVariable String Cnombre){
        return ResponseEntity.ok(prodcutoServiceImpl.obtenerProductoPorNombreCategoria(Cnombre));
    }

}
