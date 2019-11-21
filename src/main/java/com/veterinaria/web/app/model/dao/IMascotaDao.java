package com.veterinaria.web.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.veterinaria.web.app.model.entity.Mascota;
import com.veterinaria.web.app.model.entity.Persona;

public interface IMascotaDao extends PagingAndSortingRepository<Mascota, Long>{
	
	@Query("select ma from Mascota ma where ma.nombre like %?1%")
	public List<Mascota> findByNombre(String term);
	
	@Query("select ma from Mascota ma where ma.idPersona=?1")
	public List<Mascota> findByPersonaId(Long id);

	public List<Mascota> findByNombreLikeIgnoreCase(String term);
	
	@Query("select pe from Persona pe where pe.idPersona=?1")
	public Persona fetchByIdWithPersona(Long id);
	
	@Query("select pe from Persona pe where pe.dni=?1")
	public Persona selectPersonaDni(String id);
	
	@Query("select pe from Persona pe where pe.nombre like %?1% or pe.apellido like %?1%")
	public List<Persona> selectPersonaNombre(String id);
}
