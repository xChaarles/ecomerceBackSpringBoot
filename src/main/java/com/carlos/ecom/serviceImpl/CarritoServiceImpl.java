package com.carlos.ecom.serviceImpl;

import com.carlos.ecom.Entity.Carrito;
import com.carlos.ecom.Entity.DetalleOrden;
import com.carlos.ecom.Entity.Producto;
import com.carlos.ecom.Entity.User;
import com.carlos.ecom.dao.CarritoDao;
import com.carlos.ecom.dao.DetalleOrdenDao;
import com.carlos.ecom.dao.ProductoDao;
import com.carlos.ecom.dao.UserDao;
import com.carlos.ecom.dto.CarritoRequest;
import com.carlos.ecom.dto.DetalleOrdenRes;
import com.carlos.ecom.dto.OrdenRes;
import com.carlos.ecom.dto.UserCarrito;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoServiceImpl {

    @Autowired
    private CarritoDao carritoDao;

    @Autowired
    private DetalleOrdenDao detalleOrdenDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private UserDao userDao;

    public CarritoRequest addCarrito(Integer productoId, Long cantidad, Integer userId) {
        CarritoRequest carritoRequest = new CarritoRequest();
        try {
            // Obtener el producto por ID
            Optional<Producto> productoOpt = productoDao.findById(productoId);
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();

                // Validar stock disponible
                if (producto.getCantidad() >= cantidad) {
                    Optional<Carrito> carritoOpt = carritoDao.findByUserId(userId);
                    Carrito carrito;

                    // Si el carrito ya existe, lo obtenemos; de lo contrario, lo creamos
                    if (carritoOpt.isPresent()) {
                        carrito = carritoOpt.get();
                    } else {
                        carrito = new Carrito();
                        User user = userDao.findById(userId)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                        carrito.setUser(user);
                        carritoDao.save(carrito); // Guardar nuevo carrito
                    }

                    // Verificar si el producto ya está en los detalles del carrito
                    List<DetalleOrden> detalles = carrito.getDetalles();
                    if (detalles == null) {
                        detalles = new ArrayList<>();
                    }

                    Optional<DetalleOrden> detalleExistenteOpt = detalles.stream()
                            .filter(d -> d.getProducto().getId().equals(productoId))
                            .findFirst();

                    if (detalleExistenteOpt.isPresent()) {
                        // Si el producto ya está en el carrito, actualizamos la cantidad y el total
                        DetalleOrden detalleExistente = detalleExistenteOpt.get();
                        detalleExistente.setCantidad(cantidad);
                        detalleExistente.setTotal(detalleExistente.getPrecio() * detalleExistente.getCantidad());
                    } else {

                        DetalleOrden detalleOrden = new DetalleOrden();
                        detalleOrden.setId(producto.getId());
                        detalleOrden.setNombre(producto.getPnombre());
                        detalleOrden.setImgp(producto.getPimgUrl());
                        detalleOrden.setCantidad(cantidad);
                        detalleOrden.setPrecio(producto.getPrecio());
                        detalleOrden.setTotal(producto.getPrecio() * cantidad);
                        detalleOrden.setProducto(producto);
                        detalles.add(detalleOrden);

                    }

                    carrito.setDetalles(detalles);
                    carritoDao.save(carrito);

                    // Calcular el total acumulado
                    double totalAcumulado = detalles.stream()
                            .mapToDouble(DetalleOrden::getTotal)
                            .sum();

                    carritoRequest.setStatusCode(200);
                    carritoRequest.setMessage("Producto añadido al carrito");
                    carritoRequest.setId(carrito.getId());
                    carritoRequest.setTotalAcumulado(totalAcumulado);

                    // Crear y asignar el DTO del usuario
                    UserCarrito userCarrito = new UserCarrito();
                    userCarrito.setId(carrito.getUser().getId());
                    userCarrito.setNombre(carrito.getUser().getNombre());
                    userCarrito.setApellido(carrito.getUser().getApellido());
                    userCarrito.setEmail(carrito.getUser().getEmail());
                    userCarrito.setImg_url(carrito.getUser().getImg_url());
                    userCarrito.setCiudad(carrito.getUser().getCiudad());
                    userCarrito.setNumeroContacto(carrito.getUser().getNumeroContacto());
                    carritoRequest.setUserDTO(userCarrito);

                    // Crear y asignar los detalles de la orden a la respuesta
                    List<DetalleOrdenRes> detalleResList = detalles.stream()
                            .map(d -> {
                                DetalleOrdenRes detalleRes = new DetalleOrdenRes();
                                detalleRes.setId(d.getId());
                                detalleRes.setNombre(d.getNombre());
                                detalleRes.setImgp(d.getImgp());
                                detalleRes.setCantidad(d.getCantidad());
                                detalleRes.setPrecio(d.getPrecio());
                                detalleRes.setTotal(d.getTotal());
                                return detalleRes;
                            })
                            .collect(Collectors.toList());

                    carritoRequest.setDetalles(detalleResList);

                } else {
                    carritoRequest.setStatusCode(404);
                    carritoRequest.setMessage("Stock insuficiente");
                }
            } else {
                carritoRequest.setStatusCode(404);
                carritoRequest.setMessage("Producto no encontrado");
            }
        } catch (Exception e) {
            carritoRequest.setStatusCode(500);
            carritoRequest.setMessage("Ocurrió un error: " + e.getMessage());
        }
        return carritoRequest;
    }

    public CarritoRequest getCarritoPorUser(Integer userId){
        CarritoRequest carritoRequest = new CarritoRequest();
        try {
            // Buscar el carrito por el ID del usuario
            Optional<Carrito> carritoOpt = carritoDao.findByUserId(userId);

            if (carritoOpt.isPresent()) {
                Carrito carrito = carritoOpt.get();

                // Construir la respuesta
                carritoRequest.setStatusCode(200);
                carritoRequest.setMessage("Carrito encontrado");
                carritoRequest.setId(carrito.getId());

                // Crear y asignar el DTO del usuario
                UserCarrito userRes = new UserCarrito();
                userRes.setNombre(carrito.getUser().getNombre());
                userRes.setApellido(carrito.getUser().getApellido());
                userRes.setImg_url(carrito.getUser().getImg_url());
                userRes.setEmail(carrito.getUser().getEmail());
                userRes.setNumeroContacto(carrito.getUser().getNumeroContacto());
                carritoRequest.setUserDTO(userRes);

                // Crear y asignar los detalles de la orden a la respuesta
                List<DetalleOrdenRes> detalleResList = carrito.getDetalles().stream()
                        .map(d -> {
                            DetalleOrdenRes detalleRes = new DetalleOrdenRes();
                            detalleRes.setId(d.getId());
                            detalleRes.setNombre(d.getNombre());
                            detalleRes.setImgp(d.getImgp());
                            detalleRes.setCantidad(d.getCantidad());
                            detalleRes.setPrecio(d.getPrecio());
                            detalleRes.setTotal(d.getTotal());
                            return detalleRes;
                        })
                        .collect(Collectors.toList());

                carritoRequest.setDetalles(detalleResList);

                // Calcular el total acumulado
                double totalAcumulado = detalleResList.stream()
                        .mapToDouble(DetalleOrdenRes::getTotal)
                        .sum();
                carritoRequest.setTotalAcumulado(totalAcumulado);

            } else {
                carritoRequest.setStatusCode(404);
                carritoRequest.setMessage("Carrito no encontrado para el usuario");
            }
        } catch (Exception e) {
            carritoRequest.setStatusCode(500);
            carritoRequest.setMessage("Ocurrió un error: " + e.getMessage());
        }
        return carritoRequest;
    }

    public CarritoRequest deleteProductoCarrito(Integer userId, Integer detalleOrdenId){
        CarritoRequest carritoRequest = new CarritoRequest();
        try{
            Optional<Carrito> carritoOpt = carritoDao.findByUserId(userId);

            if(carritoOpt.isPresent()){
                Carrito carrito = carritoOpt.get();

                Optional<DetalleOrden> detalleOpt = detalleOrdenDao.findById(detalleOrdenId);
                if (detalleOpt.isPresent()) {
                    DetalleOrden detalle = detalleOpt.get();

                if (carrito.getDetalles().contains(detalle)) {
                    carrito.getDetalles().remove(detalle);
                    detalleOrdenDao.delete(detalle);
                    carritoDao.save(carrito);

                    double totalAcumulado = carrito.getDetalles().stream()
                            .mapToDouble(DetalleOrden::getTotal)
                            .sum();

                    carritoRequest.setStatusCode(200);
                    carritoRequest.setMessage("Producto eliminado del carrito");
                    carritoRequest.setTotalAcumulado(totalAcumulado);
                    carritoRequest.setId(carrito.getId());
                } else {
                    carritoRequest.setStatusCode(404);
                    carritoRequest.setMessage("Detalle no pertenece a este carrito");
                    }
                } else {
                    carritoRequest.setStatusCode(404);
                    carritoRequest.setMessage("Detalle no encontrado");
                }
            } else {
                carritoRequest.setStatusCode(404);
                carritoRequest.setMessage("Carrito no encontrado para el usuario");
            }
        } catch (Exception e) {
            carritoRequest.setStatusCode(500);
            carritoRequest.setMessage("Ocurrió un error: " + e.getMessage());
        }
        return carritoRequest;
    }

    @Transactional
    public OrdenRes confirmarCarrito(Integer userId) {
        OrdenRes ordenRes = new OrdenRes();
        try {
            Optional<Carrito> carritoOpt = carritoDao.findByUserId(userId);
            if (carritoOpt.isPresent()) {
                Carrito carrito = carritoOpt.get();

                // Actualizar el stock de los productos
                for (DetalleOrden detalles : carrito.getDetalles()) {
                    Producto producto = productoDao.findById(detalles.getProducto().getId())
                            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                    if (producto.getCantidad() < detalles.getCantidad()) {
                        throw new RuntimeException("Stock insuficiente para el producto: " + producto.getPnombre());
                    }

                    // Reducir el stock
                    producto.setCantidad(producto.getCantidad() - detalles.getCantidad());
                    productoDao.save(producto);
                }

                carrito.getDetalles().clear();
                carritoDao.save(carrito);

                ordenRes.setStatusCode(200);
                ordenRes.setMessage("Compra confirmada, productos actualizados y carrito eliminado");
            } else {
                ordenRes.setStatusCode(404);
                ordenRes.setMessage("Carrito no encontrado");
            }
        } catch (Exception e) {
            ordenRes.setStatusCode(500);
            ordenRes.setMessage("Error al confirmar la compra: " + e.getMessage());
        }
        return ordenRes;
    }

}
