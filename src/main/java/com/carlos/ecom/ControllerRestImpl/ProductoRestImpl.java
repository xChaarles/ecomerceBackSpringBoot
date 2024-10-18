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

import java.util.List;

@RestController
public class ProductoRestImpl implements ProductoRest {

    @Autowired
    ProdcutoServiceImpl prodcutoServiceImpl;

    @Override
    public ResponseEntity<ProductoRes> getAllProducto() {
        ProductoRes productoRes = prodcutoServiceImpl.getAllProducto();
        return ResponseEntity.status(productoRes.getStatusCode()).body(productoRes);
    }

    @Override
    public ResponseEntity<ProductoRes> getProductoById(@PathVariable Integer id){
        return ResponseEntity.ok(prodcutoServiceImpl.getProductoById(id));
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
    public List<Producto> obtenerProductoPorNombreCategoria(@PathVariable String categoriaNombre){
        return prodcutoServiceImpl.obtenerProductoPorNombreCategoria(categoriaNombre);
    }

}
