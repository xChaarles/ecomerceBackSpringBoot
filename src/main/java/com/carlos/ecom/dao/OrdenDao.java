package com.carlos.ecom.dao;


import com.carlos.ecom.Entity.Orden;
import com.carlos.ecom.Entity.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenDao extends JpaRepository<Orden, Integer> {
    List<Orden> findByUserId(Integer userId);
    Optional<Orden> deleteByNumeroOrden(String numeroOrden);
    Optional<Orden> findByNumeroOrden(String numeroOrden);
}
