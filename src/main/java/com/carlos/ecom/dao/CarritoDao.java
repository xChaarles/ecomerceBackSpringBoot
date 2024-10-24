package com.carlos.ecom.dao;

import com.carlos.ecom.Entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoDao extends JpaRepository<Carrito, Integer>{
    Optional<Carrito> findByUserId(Integer userId);
}
