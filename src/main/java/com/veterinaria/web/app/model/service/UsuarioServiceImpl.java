package com.veterinaria.web.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veterinaria.web.app.model.dao.IUsuarioDao;
import com.veterinaria.web.app.model.entity.Producto;
import com.veterinaria.web.app.model.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioDao usuarioDao;	
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioDao.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario fetchByIdWithFacturas(Long id) {
		return usuarioDao.fetchByIdWithFacturas(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		usuarioDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAll(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}

	@Override
	public List<Producto> findByNombre(String term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Producto findProductoById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFactura(Long id) {
		// TODO Auto-generated method stub
		
	}	
	
}
