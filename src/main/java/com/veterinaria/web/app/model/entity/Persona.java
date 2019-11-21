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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "personas")
public class Persona implements Serializable {

	private static final long serialVersionUID = 8403187889165947106L;

	@Id
	@Column(name = "id_persona")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idPersona;

	@NotEmpty
	@Column(name = "pe_nombre")
	private String nombre;
	
	@NotEmpty
	@Column(name = "pe_apellido")
	private String apellido;
	
	@NotEmpty
	@Column(name = "pe_dni")
	private String dni;	
	
	@Column(name = "pe_fecha_nac")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaNacimiento;
	
	@NotEmpty
	@Column(name = "pe_telefono")
	private String telefono;
	
	@Column(name = "pe_celular")
	private String celular;
	
	@NotEmpty
	@Email
	@Column(name = "pe_email")
	private String email;
	
	@NotEmpty
	@Column(name = "pe_direccion")
	private String direccion;
	
	@NotEmpty
	@Column(name = "pe_rol")
	private String rol;
	
	private String dia;
	private String mes;
	private String anio;
	private String descRol;
	private String strFecha;

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
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

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
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

	public void setAnio(String anio) {
		this.anio = anio;
	}	

	public String getDescRol() {
		return descRol;
	}

	public void setDescRol(String descRol) {
		this.descRol = descRol;
	}	

	public String getStrFecha() {
		return strFecha;
	}

	public void setStrFecha(String strFecha) {
		this.strFecha = strFecha;
	}

	@Override
	public String toString() {
		return nombre + " " + apellido;
	}
		
	public Persona(Long idPersona, @NotEmpty String nombre, @NotEmpty String apellido, @NotEmpty String dni,
			Date fechaNacimiento, @NotEmpty String telefono, @NotEmpty String celular, @NotEmpty @Email String email,
			@NotEmpty @Email String direccion, @NotEmpty String rol) {
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.celular = celular;
		this.email = email;
		this.direccion = direccion;
		this.rol = rol;
	}

	public Persona() {
		// TODO Auto-generated constructor stub
	}

	public Date toDate() throws ParseException {
		String dateAux = anio.concat("-").concat(mes).concat("-").concat(dia);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date convertedCurrentDate = sdf.parse(dateAux);
		return convertedCurrentDate;
	}
}
