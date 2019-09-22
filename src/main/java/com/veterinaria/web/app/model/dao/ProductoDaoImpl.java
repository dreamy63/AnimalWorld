package com.veterinaria.web.app.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.veterinaria.web.app.model.entity.Producto;

@Repository("productoDaoImpl")
public class ProductoDaoImpl implements IProductoDao {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Producto> listarProductos() {
		return em.createQuery("from Producto").getResultList();
	}

	@Override
	@Transactional
	public void registrarProducto(Producto producto) {
		em.persist(producto);		
	}

}
