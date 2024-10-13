package com.carlos.ecom.dao;

import com.carlos.ecom.Entity.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaDao extends JpaRepository<CategoriaProducto, Integer> {

}
