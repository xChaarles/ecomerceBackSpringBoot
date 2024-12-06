package com.carlos.ecom.ControllerRest;

import com.carlos.ecom.dto.ConfirmacionRes;
import com.carlos.ecom.dto.OrdenRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping
public interface OrdenRest {

    @PostMapping("/user/iniciar/{userId}")
    public ResponseEntity<OrdenRes> iniciarProcesoPago(@PathVariable Integer integer);

    @PostMapping("/user/confirmacion")
    public ResponseEntity<OrdenRes> confirmacionPago(@RequestBody ConfirmacionRes response);

    @GetMapping("/user/validar-transaccion/{ref_payco}")
    public ResponseEntity<?> validarTransaccion(@PathVariable String ref_payco);

    @GetMapping("/user/ordenes/{userId}")
    public ResponseEntity<List<OrdenRes>> getOrdenByUsers(@PathVariable Integer userId);

    @GetMapping("/admin/orden-all")
    public ResponseEntity<OrdenRes> getAllOrden();
}