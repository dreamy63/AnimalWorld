package com.veterinaria.web.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.veterinaria.web.app.model.entity.Persona;

public interface IPersonaDao extends PagingAndSortingRepository<Persona, Long>{
	
	@Query("select pe from Persona pe where pe.nombre like %?1%")
	public List<Persona> findByNombre(String term);

	public List<Persona> findByNombreLikeIgnoreCase(String term);

}
