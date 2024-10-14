package com.carlos.ecom.ControllerRest;

import ch.qos.logback.core.model.processor.PhaseIndicator;
import com.carlos.ecom.Entity.Producto;
import com.carlos.ecom.dto.ProductoRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
public interface ProductoRest {

    @GetMapping("/public/all-producto")
    public ResponseEntity<ProductoRes> getAllProducto();

    @GetMapping("/public/producto/{Pid}")
    public ResponseEntity<ProductoRes> getProductoById(@PathVariable Integer Pid);

    @PostMapping("/admin/create-producto")
    public ResponseEntity<ProductoRes> createProducto(@RequestBody ProductoRes reg);

    @PutMapping("/admin/update-producto/{productoId}")
    public ResponseEntity<ProductoRes> updateProducto(@PathVariable Integer productoId, @RequestBody ProductoRes reg);

    @DeleteMapping("/admin/delete-producto/{productoId}")
    public ResponseEntity<ProductoRes> deleteProducto(@PathVariable Integer productoId);

    @GetMapping("public/categoria/{Cnombre}")
    public ResponseEntity<ProductoRes> obtenerProductoPorNombreCategoria(@PathVariable String Cnombre);

}
