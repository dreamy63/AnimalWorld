package com.veterinaria.web.app.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veterinaria.web.app.model.entity.Persona;
import com.veterinaria.web.app.model.service.IPersonaService;
import com.veterinaria.web.app.util.paginator.PageRender;
import com.veterunaria.web.app.util.utils.CommonUtils;
import com.veterunaria.web.app.util.utils.Constants;

@Controller
@SessionAttributes("persona")
public class PersonaController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IPersonaService personaService;	

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/verPersona/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Persona persona = personaService.findPersonaById(id);
		if (persona == null) {
			flash.addFlashAttribute("error", "El persona no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("Persona", persona);
		model.put("titulo", "Detalle Usuario: " + persona.getNombre());
		return "ver";
	}
	
	@RequestMapping(value = {"/personas"}, method = RequestMethod.GET)
	public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, 5);
		String titulo = "";
		Page<Persona> personas = personaService.findAll(pageRequest);
		
		if(hasRole("ROLE_ADMIN")) {
			titulo = "Personas";
		} else {
			titulo = "Clientes";
		}	

		PageRender<Persona> pageRender = new PageRender<Persona>("/personas", personas);
		model.addAttribute("titulo", titulo);
		model.addAttribute("personas", personas);
		model.addAttribute("page", pageRender);
		return "personas";
	}

	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/persona")
	public String crear(Map<String, Object> model) {
		
		String titulo = "";
		if(hasRole("ROLE_ADMIN")) {
			titulo = "Registrar nueva persona";
		} else {
			titulo = "Registrar nuevo cliente";
		}
		
		Persona persona = new Persona();
		model.put("persona", persona);
		model.put("titulo", titulo);
		model.put("boton", "Registrar");
		return "registrarPersona";
	}

	@RequestMapping(value = "/registrarPersona", method = RequestMethod.POST)
	public String guardar(Persona persona, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Persona");
			return "registrarPersona";
		}
		
		if(hasRole("ROLE_ADMIN")) {
			persona.setRol(Constants.STR_ONE);
		}else{
			persona.setRol(Constants.STR_ONE);
		}
		
		String mensajeFlash = (persona.getIdPersona() != null) ? "Persona editado con éxito!" : "Persona creado con éxito!";
		
		Date date = new Date();
		try {
			if(!CommonUtils.isNullOrEmpty(persona.getAnio()) && !CommonUtils.isNullOrEmpty(persona.getMes()) && 
					!CommonUtils.isNullOrEmpty(persona.getDia()))
			date = new SimpleDateFormat("yyyy-MM-dd").parse(persona.getAnio()+"-"+persona.getMes()+"-"+persona.getDia());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		persona.setFechaNacimiento(date);
		personaService.save(persona);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/personas";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/registrarPersona/{idPersona}")
	public String editar(@PathVariable(value = "idPersona") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Persona persona = null;

		if (id > 0) {
			persona = personaService.findOne(id);
			if (persona == null) {
				flash.addFlashAttribute("error", "El ID del persona no existe!");
				return "redirect:/personas";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del persona no puede ser cero!");
			return "redirect:/personas";
		}
		model.put("persona", persona);
		model.put("titulo", "Editar persona");
		model.put("boton", "Actualizar");
		return "registrarPersona";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarPersona/{idPersona}")
	public String eliminar(@PathVariable(value = "idPersona") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Persona persona = personaService.findOne(id);

			personaService.delete(id);
			flash.addFlashAttribute("success", "Persona eliminado con éxito!");			
		}
		return "redirect:/personas";
	}
	
	private boolean hasRole(String role) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		
		if(context == null) {
			return false;
		}
		
		Authentication auth = context.getAuthentication();
		
		if(auth == null) {
			return false;
		}
		
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		return authorities.contains(new SimpleGrantedAuthority(role));
		
		/*
		 * for(GrantedAuthority authority: authorities) {
			if(role.equals(authority.getAuthority())) {
				logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: ".concat(authority.getAuthority())));
				return true;
			}
		}
		
		return false;
		*/
		
	}
}