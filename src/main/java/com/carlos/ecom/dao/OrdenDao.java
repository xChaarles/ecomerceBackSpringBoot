package com.carlos.ecom.dao;


import com.carlos.ecom.Entity.Orden;
import com.carlos.ecom.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenDao extends JpaRepository<Orden, Integer> {
    Optional<Orden> findByUser(User user);
}
