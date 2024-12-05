package com.carlos.ecom.serviceImpl;

import com.carlos.ecom.Entity.DetalleOrden;
import com.carlos.ecom.Entity.Orden;
import com.carlos.ecom.Entity.Producto;
import com.carlos.ecom.Entity.User;
import com.carlos.ecom.dao.DetalleOrdenDao;
import com.carlos.ecom.dao.OrdenDao;
import com.carlos.ecom.dao.ProductoDao;
import com.carlos.ecom.dao.UserDao;
import com.carlos.ecom.dto.DetalleOrdenRes;
import com.carlos.ecom.dto.OrdenRes;
import com.carlos.ecom.dto.ProductoRes;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetalleOrdenServiceImpl {

    @Autowired
    private DetalleOrdenDao detalleOrdenDao;

    @Autowired
    private ProductoDao productoDao;

    @Autowired
    private OrdenServideImpl ordenServiceImpl;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrdenDao ordenDao;

    public DetalleOrdenRes getDetalleOrdenUserById(Integer userId){
        DetalleOrdenRes detalleOrdenRes = new DetalleOrdenRes();

        try{
            //validacion del Usuario por Id
            User user = userDao.findById(userId).orElseThrow( () -> new RuntimeException("Usuario no encontrado"));

        }catch (Exception e){
            detalleOrdenRes.setStatusCode(500);
            detalleOrdenRes.setMessage("Ocurrio un error " + e.getMessage());
        }
        return detalleOrdenRes;
    }

    public DetalleOrdenRes deleteDetalleOrden(Integer id){
        DetalleOrdenRes detalleOrdenRes = new DetalleOrdenRes();

        try{
            Optional<DetalleOrden> detalleOrden = detalleOrdenDao.findById(id);
            if(detalleOrden.isPresent()){
                List<DetalleOrden> OrdenesNuevas = new ArrayList<DetalleOrden>();

            }else {
                detalleOrdenRes.setStatusCode(404);
                detalleOrdenRes.setMessage("DetalleOrden no encontrada");
            }
        }catch (Exception e){
            detalleOrdenRes.setStatusCode(500);
            detalleOrdenRes.setMessage("Ocurrio un Error " + e.getMessage());
        }
        return detalleOrdenRes;
    }

    public DetalleOrdenRes getDetallesPorOrden(Integer orderId) {
        DetalleOrdenRes detalleOrdenRes = new DetalleOrdenRes();
        try {
            // Buscar la orden por ID
            Optional<Orden> ordenOpt = ordenDao.findById(orderId);

            if (ordenOpt.isPresent()) {
                Orden orden = ordenOpt.get();

                // Construir la respuesta de detalles
                List<DetalleOrdenRes> detalleResList = orden.getDetalleOrdens().stream()
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

                detalleOrdenRes.setStatusCode(200);
                detalleOrdenRes.setMessage("Detalles de la orden encontrados");
                detalleOrdenRes.setOrdenId(orden.getNumeroOrden());
                detalleOrdenRes.setDetalles(detalleResList);

                // Calcular el total acumulado
                double totalAcumulado = detalleResList.stream()
                        .mapToDouble(DetalleOrdenRes::getTotal)
                        .sum();
                detalleOrdenRes.setTotalAcumulado(totalAcumulado);

            } else {
                detalleOrdenRes.setStatusCode(404);
                detalleOrdenRes.setMessage("Orden no encontrada");
            }
        } catch (Exception e) {
            detalleOrdenRes.setStatusCode(500);
            detalleOrdenRes.setMessage("Ocurrió un error: " + e.getMessage());
        }
        return detalleOrdenRes;
    }
}
