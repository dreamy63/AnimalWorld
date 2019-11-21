package com.veterinaria.web.app.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veterinaria.web.app.model.dao.IMascotaDao;
import com.veterinaria.web.app.model.entity.Mascota;
import com.veterinaria.web.app.model.entity.Persona;
import com.veterunaria.web.app.util.utils.CommonUtils;

import javassist.expr.NewArray;

@Service
public class MascotaServiceImpl implements IMascotaService{

	@Autowired
	private IMascotaDao mascotaDao;
	
//	@Autowired
//	@Qualifier("PersonaServiceImpl")
//	private PersonaServiceImpl objPersonaService;
	
	@Override
	@Transactional(readOnly = true)
	public List<Mascota> findAll() {
		// TODO Auto-generated method stub
		return (List<Mascota>) mascotaDao.findAll();
	}

	@Override
	@Transactional
	public void save(Mascota mascota) {
		mascotaDao.save(mascota);
	}

	@Override
	@Transactional(readOnly = true)
	public Mascota findOne(Long id) {
		return mascotaDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		mascotaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Mascota> findAll(Pageable pageable) {
		Page<Mascota> auxMascota = mascotaDao.findAll(pageable);
		auxMascota = CommonUtils.calcularEdad(auxMascota);
		for(Mascota objMascota : auxMascota) {
			Persona persona = mascotaDao.fetchByIdWithPersona(objMascota.getIdPersona());
			objMascota.setDuenio(persona.getNombre()+" "+persona.getApellido());
		}
		return auxMascota;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Mascota> findByNombre(String term) {
		List<Mascota> auxMascota = mascotaDao.findByNombreLikeIgnoreCase("%"+term+"%");
		auxMascota = CommonUtils.calcularEdad(auxMascota);
		for(Mascota objMascota : auxMascota) {
			Persona persona = mascotaDao.fetchByIdWithPersona(objMascota.getIdPersona());
			objMascota.setDuenio(persona.getNombre()+" "+persona.getApellido());
		}
		
		return auxMascota;
	}
	
	@Override
	@Transactional
	public Mascota findMascotaById(Long id) {
		// TODO Auto-generated method stub
		return mascotaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteMascota(Long id) {
		mascotaDao.deleteById(id);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public Persona fetchByIdWithPersona(Long id) {
		return mascotaDao.fetchByIdWithPersona(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Persona selectPersonaDni(String id) {
		return mascotaDao.selectPersonaDni(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Mascota> selectPersonaNombre(String id) {
		List<Persona> auxPersona = new ArrayList<Persona>();
		List<Mascota> auxMascota = new ArrayList<Mascota>();
		auxPersona = mascotaDao.selectPersonaNombre(id);
		for (Persona objPersona: auxPersona) {
			auxMascota.addAll(mascotaDao.findByPersonaId(objPersona.getIdPersona()));
		}
		auxMascota = CommonUtils.calcularEdad(auxMascota);
		for(Mascota objMascota : auxMascota) {
			Persona persona = mascotaDao.fetchByIdWithPersona(objMascota.getIdPersona());
			objMascota.setDuenio(persona.getNombre()+" "+persona.getApellido());
		}
		return auxMascota;
	}

	@Override
	public List<Mascota> findByPersonaId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
