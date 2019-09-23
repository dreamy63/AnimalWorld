package com.veterinaria.web.app.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.veterinaria.web.app.model.entity.Usuario;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.idUsuario=?1")
	public Usuario fetchByIdWithFacturas(Long id);
}
