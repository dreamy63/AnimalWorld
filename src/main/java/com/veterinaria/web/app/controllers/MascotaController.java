package com.veterinaria.web.app.controllers;

import java.util.Collection;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veterinaria.web.app.model.entity.Mascota;
import com.veterinaria.web.app.model.entity.Persona;
import com.veterinaria.web.app.model.service.IMascotaService;
import com.veterinaria.web.app.model.service.IPersonaService;
import com.veterinaria.web.app.util.paginator.PageRender;

@Controller
@SessionAttributes("mascota")
public class MascotaController {
	
	@Autowired
	private IPersonaService personaService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IMascotaService mascotaService;	

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/verMascota/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Mascota mascota = mascotaService.findMascotaById(id);
		if (mascota == null) {
			flash.addFlashAttribute("error", "La mascota no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("Mascota", mascota);
		model.put("titulo", "Detalle de la mascota: " + mascota.getNombre());
		return "ver";
	}
	
	@RequestMapping(value = {"/mascotas"}, method = RequestMethod.GET)
	public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, 5);
		String titulo = "Mascotas";
		Page<Mascota> mascotas = mascotaService.findAll(pageRequest);
		
		PageRender<Mascota> pageRender = new PageRender<Mascota>("/mascotas", mascotas);
		model.addAttribute("titulo", titulo);
		model.addAttribute("mascotas", mascotas);
		model.addAttribute("page", pageRender);
		return "mascotas";
	}

	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping(value = "/mascota")
	public String crear(Map<String, Object> model) {
		
		String titulo = "Registrar nueva mascota";
		Mascota mascota = new Mascota();
		model.put("mascota", mascota);
		model.put("titulo", titulo);
		model.put("boton", "Registrar");
		return "registrarMascota";
	}

	@RequestMapping(value = "/registrarMascota", method = RequestMethod.POST)
	public String guardar(Mascota mascota, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Mascota");
			return "registrarMascota";
		}
		
		String mensajeFlash = (mascota.getIdMascota() != null) ? "Mascota editado con éxito!" : "Mascota creado con éxito!";

		mascotaService.save(mascota);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/mascotas";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/registrarMascota/{idMascota}")
	public String editar(@PathVariable(value = "idMascota") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Mascota mascota = null;

		if (id > 0) {
			mascota = mascotaService.findOne(id);
			if (mascota == null) {
				flash.addFlashAttribute("error", "El ID del mascota no existe!");
				return "redirect:/mascotas";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del mascota no puede ser cero!");
			return "redirect:/mascotas";
		}
		model.put("mascota", mascota);
		model.put("titulo", "Editar mascota");
		model.put("boton", "Actualizar");
		return "registrarMascota";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarMascota/{idMascota}")
	public String eliminar(@PathVariable(value = "idMascota") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Mascota mascota = mascotaService.findOne(id);
			mascotaService.delete(id);
			flash.addFlashAttribute("success", "Mascota eliminado con éxito!");			
		}
		return "redirect:/mascotas";
	}
	
	@GetMapping(value = "/cargar-personas/{term}", produces = { "application/json" })
	public @ResponseBody List<Persona> cargarPersonas(@PathVariable String term) {
		return personaService.findByNombre(term);
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