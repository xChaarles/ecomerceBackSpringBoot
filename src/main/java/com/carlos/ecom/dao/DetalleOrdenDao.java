package com.carlos.ecom.dao;


import com.carlos.ecom.Entity.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleOrdenDao extends JpaRepository<DetalleOrden, Integer> {
}
