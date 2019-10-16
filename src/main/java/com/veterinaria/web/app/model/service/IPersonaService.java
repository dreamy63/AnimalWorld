package com.veterinaria.web.app.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.veterinaria.web.app.model.entity.Persona;

public interface IPersonaService {

	public List<Persona> findAll();
	
	public Page<Persona> findAll(Pageable pageable);

	public void save(Persona persona);
	
	public Persona findOne(Long id);
	
	public void delete(Long id);
	
	public List<Persona> findByNombre(String term);
	
	public Persona findPersonaById(Long id);
	
	public void deletePersona(Long id);

}
