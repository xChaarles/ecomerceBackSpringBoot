package com.carlos.ecom.ControllerRest;

import com.carlos.ecom.dto.DetalleOrdenRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
public interface DetalleOrdenRest {

    @GetMapping("/adminuser/detalle-orden/{ordenId}")
    public ResponseEntity<DetalleOrdenRes> getDetallesPorOrden(@PathVariable Integer ordenId);

}
