package com.carlos.ecom.ControllerRest;

import com.carlos.ecom.Entity.CategoriaProducto;
import com.carlos.ecom.dto.CategoriaProductoRes;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping
public interface CategoriaProductoRest {


    @GetMapping("/public/get-all-categoria")
    public ResponseEntity<CategoriaProductoRes> getAllCategoria();

    @GetMapping("/public/categoria/{id}")
    public ResponseEntity<CategoriaProductoRes> getCategoriaProdcutoById(@PathVariable Integer id);

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/admin/create-categoria")
    public ResponseEntity<CategoriaProductoRes> crearCategoriaProducto(@RequestBody CategoriaProductoRes reg);

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/admin/update-categoria/{categoriaCid}")
    public ResponseEntity<CategoriaProductoRes> updateCategoriaProducto(@PathVariable Integer categoriaCid, @RequestBody CategoriaProducto reg );

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/admin/delete-categoria/{categoriaCid}")
    public ResponseEntity<CategoriaProductoRes> deleteCategoriaProducto(@PathVariable Integer categoriaCid);
}
