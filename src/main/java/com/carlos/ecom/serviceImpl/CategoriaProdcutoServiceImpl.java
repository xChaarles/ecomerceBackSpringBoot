package com.carlos.ecom.serviceImpl;

import com.carlos.ecom.Entity.CategoriaProducto;
import com.carlos.ecom.dao.CategoriaDao;
import com.carlos.ecom.dto.CategoriaProductoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaProdcutoServiceImpl {

    @Autowired
    private CategoriaDao categoriaDao;

    public CategoriaProductoRes getAllCategoria(){
        CategoriaProductoRes categoriaProductoRes = new CategoriaProductoRes();
        try{
            List<CategoriaProducto> result = categoriaDao.findAll();
            if (!result.isEmpty()){
                categoriaProductoRes.setCategoriaProductoList(result);
                categoriaProductoRes.setStatusCode(200);
                categoriaProductoRes.setMessage("Exitosa");
            } else {
                categoriaProductoRes.setStatusCode(404);
                categoriaProductoRes.setMessage("No se encontraron categorias");
            }
            return categoriaProductoRes;
        }catch (Exception e) {
            categoriaProductoRes.setStatusCode(500);
            categoriaProductoRes.setMessage("Ocurrio un ERROR:"+ e.getMessage());
            return categoriaProductoRes;
        }
    }

    public CategoriaProductoRes getCategoriaProdcutoById (Integer Cid){
        CategoriaProductoRes categoriaProductoRes = new CategoriaProductoRes();
        try{
            CategoriaProducto categoriaProductoById = categoriaDao.findById(Cid).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
            categoriaProductoRes.setCategoriaProducto(categoriaProductoById);
            categoriaProductoRes.setStatusCode(200);
            categoriaProductoRes.setMessage("Categoria con id '"  + Cid + "' encontrada Exitosamente");
        }catch (Exception e){
            categoriaProductoRes.setStatusCode(500);
            categoriaProductoRes.setMessage("Ocurrio un ERROR"+ e.getMessage());
        }
        return categoriaProductoRes;
    }

    public CategoriaProductoRes crearCategoriaProducto( CategoriaProductoRes crearCategoriaRes){
        CategoriaProductoRes categoriaProductoRes = new CategoriaProductoRes();
        try {
            CategoriaProducto categoriaProducto = new CategoriaProducto();
            categoriaProducto.setCnombre(crearCategoriaRes.getCnombre());
            CategoriaProducto categoriaResult = categoriaDao.save(categoriaProducto);

            if(categoriaResult.getCid()>0){
                categoriaProductoRes.setCategoriaProducto(categoriaResult);
                categoriaProductoRes.setMessage("Nueva Categoria Creada Exitosamente");
                categoriaProductoRes.setStatusCode(200);
            }
        }catch (Exception e){
            categoriaProductoRes.setStatusCode(500);
            categoriaProductoRes.setError("Ocurrio un ERROR" + e.getMessage());
        }
        return categoriaProductoRes;
    }

    public CategoriaProductoRes updateCategoriaProducto (Integer categoriaCid, CategoriaProducto updateCategoria){
        CategoriaProductoRes categoriaProductoRes = new CategoriaProductoRes();
        try{
            Optional<CategoriaProducto> categoriaProductoOptional = categoriaDao.findById(categoriaCid);
            if(categoriaProductoOptional.isPresent()){
                CategoriaProducto existeCategoria = categoriaProductoOptional.get();
                existeCategoria.setCnombre(updateCategoria.getCnombre());

                CategoriaProducto saveCategoria = categoriaDao.save(existeCategoria);
                categoriaProductoRes.setCategoriaProducto(saveCategoria);
                categoriaProductoRes.setStatusCode(200);
                categoriaProductoRes.setMessage("Categoria Actualizada Con exito");
            }else {
                categoriaProductoRes.setStatusCode(404);
                categoriaProductoRes.setMessage("Categoria no encontrada para actualizacion");
            }
        }catch (Exception e){
            categoriaProductoRes.setStatusCode(500);
            categoriaProductoRes.setError("Ocurrio un ERROR" + e.getMessage());
        }
        return categoriaProductoRes;
    }

    public CategoriaProductoRes deleteCategoriaProducto(Integer categoriaCid){
        CategoriaProductoRes categoriaProductoRes = new CategoriaProductoRes();
        try{
            Optional<CategoriaProducto> categoriaProductoOptional = categoriaDao.findById(categoriaCid);
            if(categoriaProductoOptional.isPresent()){
                categoriaDao.deleteById(categoriaCid);
                categoriaProductoRes.setStatusCode(200);
                categoriaProductoRes.setMessage("Categoria Eliminada Con exito");
            }else {
                categoriaProductoRes.setStatusCode(404);
                categoriaProductoRes.setMessage("Categoria no encontrada");
            }
        }catch (Exception e){
            categoriaProductoRes.setStatusCode(500);
            categoriaProductoRes.setError("Ocurrio un ERROR" + e.getMessage());
        }
        return categoriaProductoRes;
    }

}
