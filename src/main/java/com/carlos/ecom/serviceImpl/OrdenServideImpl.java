package com.carlos.ecom.serviceImpl;

import com.carlos.ecom.Entity.DetalleOrden;
import com.carlos.ecom.Entity.Orden;
import com.carlos.ecom.Entity.User;
import com.carlos.ecom.dao.OrdenDao;
import com.carlos.ecom.dao.UserDao;
import com.carlos.ecom.dto.DetalleOrdenRes;
import com.carlos.ecom.dto.OrdenRes;
import com.carlos.ecom.dto.UserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdenServideImpl {

    @Autowired
    private OrdenDao ordenDao;

    @Autowired
    private UserDao userDao;



    // Método para generar un número de orden único
    private String generateOrderNumber() {
        // Lógica para generar un número de orden único
        return UUID.randomUUID().toString(); // Ejemplo usando UUID
    }
}
