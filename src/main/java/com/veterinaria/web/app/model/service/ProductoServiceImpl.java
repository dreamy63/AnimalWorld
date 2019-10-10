package com.veterinaria.web.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veterinaria.web.app.model.dao.IProductoDao;
import com.veterinaria.web.app.model.dao.IUsuarioDao;
import com.veterinaria.web.app.model.entity.Producto;
import com.veterinaria.web.app.model.entity.Usuario;
import com.veterunaria.web.app.util.utils.ParameterDetails;

@Service
public class ProductoServiceImpl implements IProductoService{

	@Autowired
	private IProductoDao productoDao;	
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		// TODO Auto-generated method stub
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Producto producto) {
		productoDao.save(producto);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findOne(Long id) {
		return productoDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		productoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> findAll(Pageable pageable) {
		Page<Producto> auxProducto = productoDao.findAll(pageable);
		for (Producto objProducto: auxProducto) {
			if(objProducto.getEstado().equals(ParameterDetails.ONE.getCodigoParametro())) {
				objProducto.setDescEstado(ParameterDetails.ONE.getDescripcion());
			}else {
				objProducto.setDescEstado(ParameterDetails.TWO.getDescripcion());
			}
		}
		return auxProducto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteProducto(Long id) {
		productoDao.deleteById(id); // facturaDao.deleteById(id);
		
	}	
	
}
