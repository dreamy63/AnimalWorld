package com.veterinaria.web.aoo.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.veterinaria.web.app.model.entity.Producto;
import com.veterinaria.web.app.model.entity.Usuario;

public interface IUsuarioService {

	public List<Usuario> findAll();
	
	public Page<Usuario> findAll(Pageable pageable);

	public void save(Usuario Usuario);
	
	public Usuario findOne(Long id);
	
	public Usuario fetchByIdWithFacturas(Long id);
	
	public void delete(Long id);
	
	public List<Producto> findByNombre(String term);
	
	public Producto findProductoById(Long id);
	
	public void deleteFactura(Long id);	

}
