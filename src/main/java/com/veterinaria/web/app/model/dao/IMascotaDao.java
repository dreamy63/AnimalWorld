package com.veterinaria.web.app.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.veterinaria.web.app.model.entity.Mascota;
import com.veterinaria.web.app.model.entity.Persona;

public interface IMascotaDao extends PagingAndSortingRepository<Mascota, Long>{
	
	@Query("select ma from Mascota ma where ma.nombre like %?1%")
	public List<Mascota> findByNombre(String term);

	public List<Mascota> findByNombreLikeIgnoreCase(String term);
	
	@Query("select pe from Persona pe where pe.idPersona=?1")
	public Persona fetchByIdWithPersona(Long id);
}
