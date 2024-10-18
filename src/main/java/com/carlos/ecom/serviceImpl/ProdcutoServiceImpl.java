package com.carlos.ecom.serviceImpl;

import com.carlos.ecom.Entity.CategoriaProducto;
import com.carlos.ecom.Entity.Producto;
import com.carlos.ecom.dao.CategoriaDao;
import com.carlos.ecom.dao.ProductoDao;
import com.carlos.ecom.dto.ProductoRes;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdcutoServiceImpl {

    @Autowired
    private CategoriaDao categoriaDao;

    @Autowired
    private ProductoDao productoDao;

    public ProductoRes getAllProducto() {
        ProductoRes productoRes = new ProductoRes();
        List<Producto> result;

        try {
            result = productoDao.findAll(); // Obtiene la lista de productos
            if (!result.isEmpty()) {
                List<ProductoRes> productosConCategoria = new ArrayList<>();

                for (Producto producto : result) {
                    ProductoRes productoItem = new ProductoRes();
                    productoItem.setId(producto.getId());
                    productoItem.setPimgUrl(producto.getPimgUrl());
                    productoItem.setPnombre(producto.getPnombre());
                    productoItem.setPdescripcion(producto.getPdescripcion());
                    productoItem.setPrecio(producto.getPrecio());
                    productoItem.setCantidad(producto.getCantidad());
                    productoItem.setCategoria(producto.getCategoria() != null ? producto.getCategoria().getCnombre() : "");
                    productosConCategoria.add(productoItem);
                }
                productoRes.setProductoList(productosConCategoria);
                productoRes.setStatusCode(200);
                productoRes.setMessage("Exitoso");
            } else {
                productoRes.setStatusCode(404);
                productoRes.setMessage("No se encontró ningún Producto");
            }
            return productoRes;
        } catch (Exception e) {
            productoRes.setStatusCode(500);
            productoRes.setMessage("Ocurrió un ERROR: " + e.getMessage());
            // Añadir log aquí para obtener más detalles
            System.err.println("Error en getAllProducto: " + e.getMessage());
            e.printStackTrace(); // Esto ayuda a depurar la excepción
            return productoRes;
        }
    }

    public ProductoRes getProductoById(Integer id){
        ProductoRes productoRes = new ProductoRes();
        try {
            Producto productoById = productoDao.findById(id).orElseThrow(() -> new RuntimeException("Prodcuto no encontrado"));
            productoRes.setProducto(productoById);
            productoRes.setStatusCode(200);
            productoRes.setMessage("Producto con id '"  + id + "' encontrada Exitosamente");
        }catch (Exception e){
            productoRes.setStatusCode(500);
            productoRes.setMessage("Ocurrio un ERROR"+ e.getMessage());
        }
        return productoRes;
    }

    public ProductoRes createProducto(ProductoRes createRes){
        ProductoRes productoRes = new ProductoRes();
        try{
            Producto producto = new Producto();
            producto.setPimgUrl(createRes.getPimgUrl());
            producto.setPnombre(createRes.getPnombre());
            producto.setPdescripcion(createRes.getPdescripcion());
            producto.setPrecio(createRes.getPrecio());
            producto.setCantidad(createRes.getCantidad());

            Optional<CategoriaProducto> categoriaOpt = categoriaDao.findByCnombre(createRes.getCategoria());
            if(categoriaOpt.isPresent()){
                producto.setCategoria(categoriaOpt.get());
            }else {
                throw new EntityNotFoundException("Categoria no encontrada: " + createRes.getCategoria());
            }
            Producto productoResult = productoDao.save(producto);
            if (productoResult.getId()>0){
                productoRes.setProducto(productoResult);
                productoRes.setMessage("Nuevo Producto creado Exitosamente");
                productoRes.setStatusCode(200);
            }
        }catch (Exception e){
            productoRes.setStatusCode(500);
            productoRes.setError("Ocurrio un ERROR" + e.getMessage());
        }
        return productoRes;
    }

    public ProductoRes updateProducto (Integer productoId, ProductoRes updateProducto){
        ProductoRes productoRes = new ProductoRes();
        try{
            Optional<Producto> productoOptional = productoDao.findById(productoId);
            if(productoOptional.isPresent()){
                Producto existeProducto = productoOptional.get();
                existeProducto.setPimgUrl(updateProducto.getPimgUrl());
                existeProducto.setPnombre(updateProducto.getPnombre());
                existeProducto.setPdescripcion(updateProducto.getPdescripcion());
                existeProducto.setPrecio(updateProducto.getPrecio());
                existeProducto.setCantidad(updateProducto.getCantidad());

                Optional<CategoriaProducto> categoriaOpt = categoriaDao.findByCnombre(updateProducto.getCategoria());
                if(categoriaOpt.isPresent()){
                    existeProducto.setCategoria(categoriaOpt.get());
                }else {
                    throw new EntityNotFoundException("Categoria no encontrada: " + productoRes.getCategoria());
                }

                Producto saveProducto = productoDao.save(existeProducto);
                productoRes.setProducto(saveProducto);
                productoRes.setStatusCode(200);
                productoRes.setMessage("Producto Actualizado con exito");
            }else {
                productoRes.setStatusCode(404);
                productoRes.setMessage("Producto no encontrado para actualizar");
            }
        }catch (Exception e){
            productoRes.setStatusCode(500);
            productoRes.setError("Ocurrio un ERROR" + e.getMessage());
        }
        return productoRes;
    }

    public ProductoRes deleteProducto(Integer productoId){
        ProductoRes productoRes = new ProductoRes();
        try{
            Optional<Producto> productoOptional = productoDao.findById(productoId);
            if(productoOptional.isPresent()){
                productoDao.deleteById(productoId);
                productoRes.setStatusCode(200);
                productoRes.setMessage("Categoria Eliminada Con exito");
            }else {
                productoRes.setStatusCode(404);
                productoRes.setMessage("Producto no encontrado");
            }
        }catch (Exception e){
            productoRes.setStatusCode(500);
            productoRes.setError("Ocurrio un ERROR" + e.getMessage());
        }
        return productoRes;
    }

    public List<Producto> obtenerProductoPorNombreCategoria(String categoriaNombre){
       return productoDao.findByCategoriaCnombre(categoriaNombre);
    }

}
