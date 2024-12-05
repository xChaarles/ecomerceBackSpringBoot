package com.carlos.ecom.ControllerRestImpl;

import com.carlos.ecom.ControllerRest.DetalleOrdenRest;
import com.carlos.ecom.dto.DetalleOrdenRes;
import com.carlos.ecom.serviceImpl.DetalleOrdenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetalleOrdenRestImpl implements DetalleOrdenRest {

    @Autowired
    DetalleOrdenServiceImpl detalleOrdenServiceImpl;

    @Override
    public ResponseEntity<DetalleOrdenRes> getDetallesPorOrden(@PathVariable Integer ordenId){
        return ResponseEntity.ok(detalleOrdenServiceImpl.getDetallesPorOrden(ordenId));
    }

}
