package com.veterinaria.web.app.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.veterinaria.web.app.model.entity.Producto;

public interface IProductoService {

	public List<Producto> findAll();
	
	public Page<Producto> findAll(Pageable pageable);

	public void save(Producto producto);
	
	public Producto findOne(Long id);
	
	public void delete(Long id);
	
	public List<Producto> findByNombre(String term);
	
	public Producto findProductoById(Long id);
	
	public void deleteProducto(Long id);

}
