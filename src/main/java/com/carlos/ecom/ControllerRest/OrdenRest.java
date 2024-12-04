package com.carlos.ecom.ControllerRest;

import com.carlos.ecom.dto.ConfirmacionRes;
import com.carlos.ecom.dto.OrdenRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
public interface OrdenRest {

    @PostMapping("/user/iniciar/{userId}")
    public ResponseEntity<OrdenRes> iniciarProcesoPago(@PathVariable Integer integer);

    @PostMapping("/user/confirmacion")
    public ResponseEntity<OrdenRes> confirmacionPago(@RequestBody ConfirmacionRes response);

    @GetMapping("/user/validar-transaccion/{ref_payco}")
    public ResponseEntity<?> validarTransaccion(@PathVariable String ref_payco);
}