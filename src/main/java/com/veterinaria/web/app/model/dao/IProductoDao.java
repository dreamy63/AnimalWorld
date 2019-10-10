package com.veterinaria.web.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.veterinaria.web.app.model.entity.Producto;

public interface IProductoDao extends PagingAndSortingRepository<Producto, Long>{
	
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);

	public List<Producto> findByNombreLikeIgnoreCase(String term);

}
