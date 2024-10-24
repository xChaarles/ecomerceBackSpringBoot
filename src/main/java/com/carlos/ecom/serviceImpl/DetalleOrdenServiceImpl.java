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
}
