package com.veterinaria.web.app.controllers;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.veterinaria.web.app.model.dao.IProductoDao;
import com.veterinaria.web.app.model.entity.Producto;

@Controller
public class ProductoController {

	@Autowired
	@Qualifier("productoDaoImpl")
	private IProductoDao productoDao;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/listaProductos" }, method = RequestMethod.GET)
	public String listarProductos(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication, HttpServletRequest request) {

		// Spring Security
		if (authentication != null) {
			logger.info("Hola usuario autenticado, tu username es: ".concat(authentication.getName()));
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			logger.info(
					"Utilizando forma estática SecurityContextHolder.getContext().getAuthentication(): Usuario autenticado: "
							.concat(auth.getName()));
		}

		if (hasRole("ROLE_ADMIN")) {
			logger.info("Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request,
				"");

		if (securityContext.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName())
					.concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando SecurityContextHolderAwareRequestWrapper: Hola ".concat(auth.getName())
					.concat(" NO tienes acceso!"));
		}

		if (request.isUserInRole("ROLE_ADMIN")) {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" tienes acceso!"));
		} else {
			logger.info("Forma usando HttpServletRequest: Hola ".concat(auth.getName()).concat(" NO tienes acceso!"));
		}

		Pageable pageRequest = PageRequest.of(page, 4);

		model.addAttribute("titulo", "Lista de productos");
		model.addAttribute("productos", productoDao.listarProductos());
		return "listaProductos";
	}

//	public String listarProductos(Model model) {
//		model.addAttribute("titulo","Lista de productos");
//		model.addAttribute("productos", productoDao.listarProductos());		
//		return "listaProductos";
//	}

	@RequestMapping(value = "/registrarProducto")
	public String crearProducto(Map<String, Object> model) {
		Producto producto = new Producto();
		model.put("producto", producto);
		model.put("titulo", "Formulario de Producto");
		return "registrarProducto";
	}

	@RequestMapping(value = "/registrarProducto", method = RequestMethod.POST)
	public String guardarProducto(Producto producto) {
		productoDao.registrarProducto(producto);
		return "redirect:listaProductos";
	}

	private boolean hasRole(String role) {

		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return false;
		}

		Authentication auth = context.getAuthentication();

		if (auth == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		return authorities.contains(new SimpleGrantedAuthority(role));

		/*
		 * for(GrantedAuthority authority: authorities) {
		 * if(role.equals(authority.getAuthority())) {
		 * logger.info("Hola usuario ".concat(auth.getName()).concat(" tu role es: "
		 * .concat(authority.getAuthority()))); return true; } }
		 * 
		 * return false;
		 */

	}

}
