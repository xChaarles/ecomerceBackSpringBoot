package com.carlos.ecom.dao;


import com.carlos.ecom.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoDao extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoriaCnombre(String categoriaNombre);
}
