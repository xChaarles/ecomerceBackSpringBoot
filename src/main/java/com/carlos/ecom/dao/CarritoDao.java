package com.carlos.ecom.dao;

import com.carlos.ecom.Entity.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoDao extends JpaRepository<Carrito, Integer>{
    @Query("SELECT c FROM Carrito c JOIN FETCH c.detalles WHERE c.user.id = :userId")
    Optional<Carrito> findByUserId(@Param("userId") Integer userId);

    @Modifying
    @Query("DELETE FROM Carrito c WHERE c.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
}
