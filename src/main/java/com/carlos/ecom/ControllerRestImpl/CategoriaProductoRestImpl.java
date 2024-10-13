package com.carlos.ecom.ControllerRestImpl;

import com.carlos.ecom.ControllerRest.CategoriaProductoRest;
import com.carlos.ecom.Entity.CategoriaProducto;
import com.carlos.ecom.dto.CategoriaProductoRes;
import com.carlos.ecom.serviceImpl.CategoriaProdcutoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaProductoRestImpl implements CategoriaProductoRest {

    @Autowired
    CategoriaProdcutoServiceImpl categoriaProdcutoServiceImpl;

    @Override
    public ResponseEntity<CategoriaProductoRes> getAllCategoria(){
        return ResponseEntity.ok(categoriaProdcutoServiceImpl.getAllCategoria());
    }

    @Override
    public ResponseEntity<CategoriaProductoRes> getCategoriaProdcutoById(@PathVariable Integer id){
        return ResponseEntity.ok(categoriaProdcutoServiceImpl.getCategoriaProdcutoById(id));
    }

    @Override
    public ResponseEntity<CategoriaProductoRes> crearCategoriaProducto(@RequestBody CategoriaProductoRes reg){
        return ResponseEntity.ok(categoriaProdcutoServiceImpl.crearCategoriaProducto(reg));
    }

    @Override
    public ResponseEntity<CategoriaProductoRes> updateCategoriaProducto(@PathVariable Integer categoriaCid, @RequestBody CategoriaProducto reg){
        return ResponseEntity.ok(categoriaProdcutoServiceImpl.updateCategoriaProducto(categoriaCid, reg));
    }

    @Override
    public ResponseEntity<CategoriaProductoRes> deleteCategoriaProducto(@PathVariable Integer categoriaCid){
        return ResponseEntity.ok(categoriaProdcutoServiceImpl.deleteCategoriaProducto(categoriaCid));
    }
}
