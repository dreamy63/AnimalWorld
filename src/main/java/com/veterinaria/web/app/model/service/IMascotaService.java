package com.veterinaria.web.app.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.veterinaria.web.app.model.entity.Mascota;
import com.veterinaria.web.app.model.entity.Persona;

public interface IMascotaService {

	public List<Mascota> findAll();
	
	public Page<Mascota> findAll(Pageable pageable);

	public void save(Mascota mascota);
	
	public Mascota findOne(Long id);
	
	public void delete(Long id);
	
	public List<Mascota> findByNombre(String term);
		
	public Mascota findMascotaById(Long id);
	
	public void deleteMascota(Long id);
	
	public Persona fetchByIdWithPersona(Long id);
	
	public Persona selectPersonaDni(String id);
	
	public List<Mascota> selectPersonaNombre(String id);
	
	public List<Mascota> findByPersonaId(Long id);

}
