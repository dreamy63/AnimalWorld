package com.veterunaria.web.app.util.utils;

public enum ParameterDetails {
	
	ONE(1,1,"Activo"),
	TWO(2,1,"Inactivo"),
	THREE(3,2,"Juguetes"),
	FOUR(4,2,"Vacunas");
	
	private Integer codigoParametroDet;
	private Integer codigoParametro;
	private String descripcion;
	
	private ParameterDetails(Integer codigoParametroDet, Integer codigoParametro, String descripcion) {
		this.codigoParametroDet = codigoParametroDet;
		this.codigoParametro = codigoParametro;
		this.descripcion = descripcion;
	}
	
	public Integer getCodigoParametroDet() {
		return codigoParametroDet;
	}
	public void setCodigoParametroDet(Integer codigoParametroDet) {
		this.codigoParametroDet = codigoParametroDet;
	}
	public Integer getCodigoParametro() {
		return codigoParametro;
	}
	public void setCodigoParametro(Integer codigoParametro) {
		this.codigoParametro = codigoParametro;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
	
}
