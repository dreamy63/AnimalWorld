package com.veterinaria.web.app.model.dao;

import java.util.List;

import com.veterinaria.web.app.model.entity.Producto;

public interface IProductoDao {
	
	public List<Producto> listarProductos();
	
	public void registrarProducto (Producto producto);

}
