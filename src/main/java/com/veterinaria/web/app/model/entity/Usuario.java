package com.veterinaria.web.app.model.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 8403187889165947106L;

	@Id
	@Column(name = "id_usuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;

	@NotEmpty
	@Column(name = "usu_nombre")
	private String nombre;
	
	@NotEmpty
	@Column(name = "usu_apellido")
	private String apellido;
	
	@NotEmpty
	@Column(name = "usu_dni")
	private String dni;	
	
	@Column(name = "usu_fecha_nac")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaNacimiento;
	
	@NotEmpty
	@Column(name = "usu_telefono")
	private String telefono;
	
	@NotEmpty
	@Column(name = "usu_celular")
	private String celular;
	
	@NotEmpty
	@Email
	@Column(name = "usu_email")
	private String email;	

	@NotEmpty
	@Column(name = "usu_usuario")
	private String usuario;
	
	@NotEmpty
	@Column(name = "usu_clave")
	private String clave;
	
	@Column(name = "usu_rol")
	private String rol;
	
	@Column(name = "usu_foto")
	private String foto;
	
	private String dia;
	private String mes;
	private String anio;
	private String direccion;
	
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) throws ParseException {
		this.fechaNacimiento = toDate();
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}	

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAnio() {
		return anio;
	}

	public void setAño(String anio) {
		this.anio = anio;
	}	

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return nombre + " " + apellido;
	}
	
	public Date toDate() throws ParseException {
		String dateAux = anio.concat("-").concat(mes).concat("-").concat(dia);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedCurrentDate = sdf.parse(dateAux);
		return convertedCurrentDate;
	}
}
