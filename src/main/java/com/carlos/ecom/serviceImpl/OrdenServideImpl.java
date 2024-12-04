package com.carlos.ecom.serviceImpl;

import com.carlos.ecom.Entity.*;
import com.carlos.ecom.dao.*;
import com.carlos.ecom.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrdenServideImpl {

    @Autowired
    private OrdenDao ordenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private CarritoDao carritoDao;

    @Autowired
    private DetalleOrdenDao detalleOrdenDao;

    @Autowired
    private CarritoServiceImpl carritoServiceImpl;

    private static final String EPAYCO_URL = "https://secure.epayco.co/validation/v1/reference/";

    // Método para generar un número de orden único
    public String generarNumeroOrden() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String randomSuffix = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "ORD-" + timestamp + "-" + randomSuffix;
    }

    public OrdenRes iniciarProcesoPago(Integer userId) {
        OrdenRes ordenRes = new OrdenRes();
        try {
            User user = userDao.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            String numeroOrden = generarNumeroOrden();

            Orden orden = new Orden();
            orden.setNumeroOrden(numeroOrden);
            orden.setFechaCreacion(LocalDateTime.now());
            orden.setUser(user);
            orden.setEstado("PENDIENTE");
            ordenDao.save(orden);

            ordenRes.setNumeroOrden(numeroOrden);
            ordenRes.setFechaCreacion(orden.getFechaCreacion());
            ordenRes.setEstado(orden.getEstado());
            ordenRes.setStatusCode(200);
            ordenRes.setMessage("Orden creada exitosamente");

        } catch (UsernameNotFoundException e) {
            ordenRes.setStatusCode(404);
            ordenRes.setMessage(e.getMessage());
        } catch (Exception e) {
            ordenRes.setStatusCode(500);
            ordenRes.setMessage("Ocurrió un error: " + e.getMessage());
        }
        return ordenRes;
    }

    public Map<String, Object> obtenerDetallesPago(String ref_payco) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofSeconds(5))
                .setReadTimeout(Duration.ofSeconds(5))
                .build();
        ;
        try {
            String url = EPAYCO_URL + ref_payco;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar los detalles de pago: " + e.getMessage());
        }
        return null;
    }

    @Transactional
    public OrdenRes confirmacionPago(Integer userId, String ref_payco) {
        OrdenRes ordenRes = new OrdenRes();
        try {
            // Obtener detalles de pago desde ePayco
            Map<String, Object> detallespago = obtenerDetallesPago(ref_payco);
            if (detallespago != null) {
                // Validar los detalles de pago
                Map<String, Object> data = (Map<String, Object>) detallespago.get("data");
                String respuesta = (String) data.get("x_response");
                if(data != null){
                    String invoice = (String) data.get("x_id_invoice");
                    if ("Aceptada".equals(respuesta)) {
                        // Buscar la orden con el número de orden recibido
                        Orden orden = ordenDao.findByNumeroOrden(invoice)
                                .orElseThrow(() -> new RuntimeException("Número de orden no encontrada"));

                        // Actualizar estado de la orden
                        orden.setEstado("COMPLETADA");
                        actualizarOrden(orden);

                        // Actualizar stock y limpiar carrito
                        OrdenRes confirmarCarrito = carritoServiceImpl.confirmarCarrito(userId);
                        System.out.println(confirmarCarrito);

                        if (confirmarCarrito.getStatusCode() == 200) {
                            ordenRes = confirmarCarrito;
                            ordenRes.setMessage("Pago confirmado y compra completada exitosamente");
                        } else {
                            ordenRes.setStatusCode(500);
                            ordenRes.setMessage("Error al cargar el carrito");
                        }
                    }else {
                        eliminarOrdenPorNumero(invoice);
                        ordenRes.setStatusCode(400);
                        ordenRes.setMessage("El numero de orden no fue encontrado: " +  invoice);
                    }
                 } else {
                    ordenRes.setStatusCode(400);
                    ordenRes.setMessage("El pago no fue exitoso, estado: " + respuesta);
                }
            } else {
                ordenRes.setStatusCode(404);
                ordenRes.setMessage("No se encontró la referencia de ePayco: " + ref_payco);
            }
        } catch (Exception e) {
            ordenRes.setStatusCode(500);
            ordenRes.setMessage("Ocurrió un error: " + e.getMessage());
        }
        return ordenRes;
    }

    public void actualizarOrden(Orden orden) {
        try {
            if (orden != null) {
                // Actualizamos la orden en la base de datos
                ordenDao.save(orden);
                System.out.println("Orden actualizada con éxito");
            } else {
                throw new RuntimeException("La orden no existe");
            }
        } catch (Exception e) {
            // En caso de error, lanzamos una excepción
            throw new RuntimeException("Error al actualizar la orden: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarOrdenPorNumero(String numeroOrden) {
        ordenDao.deleteByNumeroOrden(numeroOrden);
    }

    public List<OrdenRes> getOrdenByUsers(Integer userId){
        List<OrdenRes> ordenList = new ArrayList<>();
        try{
            List<Orden> ordenes = ordenDao.findByUserId(userId);
            if (!ordenes.isEmpty()) {
                for (Orden orden : ordenes) {
                    OrdenRes ordenRes = new OrdenRes();
                    ordenRes.setStatusCode(200);
                    ordenRes.setMessage("Órden encontrada.");
                    ordenRes.setNumeroOrden(orden.getNumeroOrden());
                    ordenRes.setFechaCreacion(orden.getFechaCreacion());
                    // Agregar más detalles si es necesario.
                    ordenList.add(ordenRes);
                }
            } else {
                OrdenRes ordenRes = new OrdenRes();
                ordenRes.setStatusCode(404);
                ordenRes.setMessage("No tienes órdenes aún.");
                ordenList.add(ordenRes);
            }
        }catch (Exception e){
            OrdenRes errorRes = new OrdenRes();
            errorRes.setStatusCode(500);
            errorRes.setMessage("Ocurrió un error: " + e.getMessage());
            ordenList.add(errorRes);
        }
        return ordenList;
    }
}
