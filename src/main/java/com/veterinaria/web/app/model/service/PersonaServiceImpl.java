package com.veterinaria.web.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veterinaria.web.app.model.dao.IPersonaDao;
import com.veterinaria.web.app.model.dao.IUsuarioDao;
import com.veterinaria.web.app.model.entity.Persona;
import com.veterinaria.web.app.model.entity.Usuario;
import com.veterunaria.web.app.util.utils.CommonUtils;
import com.veterunaria.web.app.util.utils.ParameterDetails;

@Service
public class PersonaServiceImpl implements IPersonaService{

	@Autowired
	private IPersonaDao personaDao;	
	
	@Override
	@Transactional(readOnly = true)
	public List<Persona> findAll() {
		// TODO Auto-generated method stub
		return (List<Persona>) personaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Persona persona) {
		personaDao.save(persona);
	}

	@Override
	@Transactional(readOnly = true)
	public Persona findOne(Long id) {
		return personaDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		personaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Persona> findAll(Pageable pageable) {
		Page<Persona> auxPersona = personaDao.findAll(pageable);
		auxPersona = CommonUtils.setInitalValues(auxPersona);
		return auxPersona;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Persona> findByNombre(String term) {
		return personaDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public Persona findPersonaById(Long id) {
		// TODO Auto-generated method stub
		return personaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deletePersona(Long id) {
		personaDao.deleteById(id); // facturaDao.deleteById(id);
		
	}
	
}
