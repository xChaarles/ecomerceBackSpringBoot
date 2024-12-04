package com.carlos.ecom.ControllerRestImpl;

import com.carlos.ecom.ControllerRest.OrdenRest;
import com.carlos.ecom.dto.ConfirmacionRes;
import com.carlos.ecom.dto.OrdenRes;
import com.carlos.ecom.serviceImpl.OrdenServideImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class OrdenRestImpl implements OrdenRest {

    @Autowired
    OrdenServideImpl ordenServideImpl;

    @Override
    public ResponseEntity<OrdenRes> iniciarProcesoPago(@PathVariable Integer userId){
        return ResponseEntity.ok(ordenServideImpl.iniciarProcesoPago(userId));
    }

    @Override
    public ResponseEntity<OrdenRes> confirmacionPago(@RequestBody ConfirmacionRes response ){
        return ResponseEntity.ok(ordenServideImpl.confirmacionPago(response.getUserId(), response.getRef_payco()));
    }

    @Override
    public ResponseEntity<?> validarTransaccion(@PathVariable String ref_payco) {
        try {
            Map<String, Object> detalles = ordenServideImpl.obtenerDetallesPago(ref_payco);
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al validar la transacción: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<OrdenRes>> getOrdenByUsers(@PathVariable Integer userId){
        return  ResponseEntity.ok(ordenServideImpl.getOrdenByUsers(userId));
    }

}
