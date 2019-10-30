package com.veterinaria.web.app.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="mascotas")
public class Mascota implements Serializable{

	private static final long serialVersionUID = -1778291452931750038L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mascota")
	private Long idMascota;
	
	@NotEmpty
	@Column(name = "ma_nombre")
	private String nombre;
	
	@NotEmpty
	@Column(name = "id_tipo_mascota")
	private Integer tipoMascota;
	
	@Column(name = "ma_raza")
	private String raza;
	
	@Column(name = "ma_sexo")
	private String sexo;
	
	@Column(name = "ma_fecha_nac")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date mascotaFechaNac;
	
	@Column(name = "ma_color")
	private String color;
	
	@NotEmpty
	@Column(name = "ma_estado")
	private Integer estado;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_persona")
	private Persona persona;
	
	private String edad;
	private String etiquetaTipo;
	private String etiquetaEstado;
	private String dia;
	private String mes;
	private String anio;

	public Long getIdMascota() {
		return idMascota;
	}

	public void setIdMascota(Long idMascota) {
		this.idMascota = idMascota;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getTipoMascota() {
		return tipoMascota;
	}

	public void setTipoMascota(Integer tipoMascota) {
		this.tipoMascota = tipoMascota;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getMascotaFechaNac() {
		return mascotaFechaNac;
	}

	public void setMascotaFechaNac(Date mascotaFechaNac) {
		this.mascotaFechaNac = mascotaFechaNac;
	}	

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}	

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getEtiquetaTipo() {
		return etiquetaTipo;
	}

	public void setEtiquetaTipo(String etiquetaTipo) {
		this.etiquetaTipo = etiquetaTipo;
	}

	public String getEtiquetaEstado() {
		return etiquetaEstado;
	}

	public void setEtiquetaEstado(String etiquetaEstado) {
		this.etiquetaEstado = etiquetaEstado;
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

}
