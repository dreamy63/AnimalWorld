package com.veterinaria.web.app.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import com.veterinaria.web.app.model.beans.QueryHistoria;
import com.veterinaria.web.app.model.entity.Mascota;
import com.veterinaria.web.app.model.entity.Persona;
import com.veterinaria.web.app.model.service.IMascotaService;
import com.veterinaria.web.app.model.service.IPersonaService;
import com.veterinaria.web.app.util.paginator.PageRender;
import com.veterunaria.web.app.util.utils.CommonUtils;
import com.veterunaria.web.app.util.utils.Constants;

@Controller
@SessionAttributes("mascota")
public class HistoriaController {
	
	@Autowired
	private IPersonaService personaService;
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IMascotaService mascotaService;	

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/verHistoria/{id}")
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
	
	@RequestMapping(value = {"/historias"}, method = RequestMethod.GET)
	public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, 5);
		String titulo = "Mascotas";
		Page<Mascota> mascotas = mascotaService.findAll(pageRequest);
		QueryHistoria query = new QueryHistoria();
		
		PageRender<Mascota> pageRender = new PageRender<Mascota>("/mascotas", mascotas);
		model.addAttribute("titulo", titulo);
		model.addAttribute("query", query);
		model.addAttribute("boton", "Buscar");
		model.addAttribute("mascotas", mascotas);
		model.addAttribute("page", pageRender);
		return "historias";
	}
	
	@RequestMapping(value = "/buscarHistoria", method = RequestMethod.POST)
	public String guardar(QueryHistoria query, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		Pageable pageRequest = PageRequest.of(0, 5);
		String titulo = "Mascotas";
		List<Mascota> mascotas = new ArrayList<>();
		if(!CommonUtils.isNullOrEmpty(query.getNombreMascota()) && CommonUtils.isNullOrEmpty(query.getNombreDuenio())) {
			mascotas = mascotaService.findByNombre(query.getNombreMascota());
		}else if(CommonUtils.isNullOrEmpty(query.getNombreMascota()) && !CommonUtils.isNullOrEmpty(query.getNombreDuenio())) {
			mascotas = mascotaService.selectPersonaNombre(query.getNombreDuenio());
		}else if(!CommonUtils.isNullOrEmpty(query.getNombreMascota()) && !CommonUtils.isNullOrEmpty(query.getNombreDuenio())) {
			
			
		}
		
		
		int start = 0;
		int end = (start + 5) > mascotas.size() ? mascotas.size() : (start + 5);
		Page<Mascota> pages = new PageImpl<Mascota>(mascotas.subList(start, end), pageRequest , mascotas.size());
		
		PageRender<Mascota> pageRender = new PageRender<Mascota>("/mascotas", pages);
		model.addAttribute("titulo", titulo);
		model.addAttribute("query", query);
		model.addAttribute("boton", "Buscar");
		model.addAttribute("mascotas", mascotas);
		model.addAttribute("page", pageRender);
		return "historias";
	}
	
	@GetMapping(value = "/cargar-historias/{term}", produces = { "application/json" })
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