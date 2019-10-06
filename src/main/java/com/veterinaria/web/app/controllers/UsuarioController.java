package com.veterinaria.web.app.controllers;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veterinaria.web.app.model.entity.Usuario;
import com.veterinaria.web.app.model.service.IUsuarioService;
import com.veterinaria.web.app.util.paginator.PageRender;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IUsuarioService usuarioService;	

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = usuarioService.fetchByIdWithFacturas(id);
		if (usuario == null) {
			flash.addFlashAttribute("error", "El Usuario no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("Usuario", usuario);
		model.put("titulo", "Detalle Usuario: " + usuario.getNombre());
		return "ver";
	}

	@RequestMapping(value = {"/listar"}, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		if(authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if(auth != null) {
			logger.info("Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: ".concat(auth.getName()));
		}
		
		if(hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}
		
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
		
		if(securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		if(request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}	
		
		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);

		PageRender<Usuario> pageRender = new PageRender<Usuario>("/listar", usuarios);
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("clientes", usuarios);
		model.addAttribute("page", pageRender);
		return "index";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/registrarUsuario")
	public String crear(Map<String, Object> model) {

		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		model.put("titulo", "Registrar usuario");
		model.put("boton", "Registrar");
		return "registrarUsuario";
	}	

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/registrarUsuario", method = RequestMethod.POST)
	public String guardar(Usuario usuario, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Usuario");
			return "registrarUsuario";
		}

		String mensajeFlash = (usuario.getIdUsuario() != null) ? "Usuario editado con éxito!" : "Usuario creado con éxito!";

		usuarioService.save(usuario);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/usuarios";
	}
	
	@RequestMapping(value = {"/usuarios"}, method = RequestMethod.GET)
	public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Usuario> usuarios = usuarioService.findAll(pageRequest);

		PageRender<Usuario> pageRender = new PageRender<Usuario>("/usuarios", usuarios);
		model.addAttribute("titulo", "Listado de usuarios");
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
		return "usuarios";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = null;

		if (id > 0) {
			usuario = usuarioService.findOne(id);
			if (usuario == null) {
				flash.addFlashAttribute("error", "El ID del Usuario no existe en la BBDD!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del Usuario no puede ser cero!");
			return "redirect:/listar";
		}
		model.put("Usuario", usuario);
		model.put("titulo", "Editar Usuario");
		return "form";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Usuario usuario = usuarioService.findOne(id);

			usuarioService.delete(id);
			flash.addFlashAttribute("success", "Usuario eliminado con éxito!");			
		}
		return "redirect:/listar";
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
