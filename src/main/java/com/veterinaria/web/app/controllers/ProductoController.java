package com.veterinaria.web.app.controllers;

import java.util.Collection;
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

import com.veterinaria.web.app.model.entity.Producto;
import com.veterinaria.web.app.model.service.IProductoService;
import com.veterinaria.web.app.util.paginator.PageRender;

@Controller
@SessionAttributes("producto")
public class ProductoController {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private IProductoService productoService;	

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/verProducto/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Producto producto = productoService.findProductoById(id);
		if (producto == null) {
			flash.addFlashAttribute("error", "El producto no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("Producto", producto);
		model.put("titulo", "Detalle Usuario: " + producto.getNombre());
		return "ver";
	}
	
	@RequestMapping(value = {"/productos"}, method = RequestMethod.GET)
	public String listarUsuarios(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			Authentication authentication,
			HttpServletRequest request) {

		Pageable pageRequest = PageRequest.of(page, 4);

		Page<Producto> productos = productoService.findAll(pageRequest);

		PageRender<Producto> pageRender = new PageRender<Producto>("/productos", productos);
		model.addAttribute("titulo", "Productos");
		model.addAttribute("productos", productos);
		model.addAttribute("page", pageRender);
		return "productos";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/producto")
	public String crear(Map<String, Object> model) {
		Producto producto = new Producto();
		model.put("producto", producto);
		model.put("titulo", "Registrar nuevo producto");
		model.put("boton", "Registrar");
		return "registrarProducto";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/registrarProducto", method = RequestMethod.POST)
	public String guardar(Producto producto, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de Producto");
			return "registrarProducto";
		}

		String mensajeFlash = (producto.getIdProducto() != null) ? "Producto editado con éxito!" : "Producto creado con éxito!";

		productoService.save(producto);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/productos";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/registrarProducto/{idProducto}")
	public String editar(@PathVariable(value = "idProducto") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Producto producto = null;

		if (id > 0) {
			producto = productoService.findOne(id);
			if (producto == null) {
				flash.addFlashAttribute("error", "El ID del producto no existe!");
				return "redirect:/productos";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del producto no puede ser cero!");
			return "redirect:/productos";
		}
		model.put("producto", producto);
		model.put("titulo", "Editar producto");
		model.put("boton", "Actualizar");
		return "registrarProducto";
	}

	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminarProducto/{idProducto}")
	public String eliminar(@PathVariable(value = "idProducto") Long id, RedirectAttributes flash) {

		if (id > 0) {
			Producto producto = productoService.findOne(id);

			productoService.delete(id);
			flash.addFlashAttribute("success", "Producto eliminado con éxito!");			
		}
		return "redirect:/productos";
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