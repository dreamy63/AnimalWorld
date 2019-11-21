package com.veterunaria.web.app.util.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.veterinaria.web.app.model.entity.Mascota;
import com.veterinaria.web.app.model.entity.Persona;

public class CommonUtils {
	
	public static boolean isNullOrEmpty(Object objGeneric) {
        boolean blResult = isNull(objGeneric);
        if(!blResult){
        	blResult = isEmpty(objGeneric.toString());
        }
        return blResult;
    }
	
	public static boolean isNull(Object objGeneric) {
        return (objGeneric == null);
    }
	
	public static boolean isEmpty(String strText) {
        return (Constants.STR_EMPTY.equals(strText));
    }
	
	public static String convertDatetoString(Date dtDate, String strFormat) {
		String strNewDate = Constants.STR_EMPTY;
		try {
			SimpleDateFormat objSdf = new SimpleDateFormat(strFormat);
			if (dtDate != null)
				strNewDate = objSdf.format(dtDate);
		} catch (Exception e) {
			return null;
		}
		return strNewDate;
	}
	
	public static Page<Persona> setInitalValues (Page<Persona> persona) {		
		for (Persona objPersona: persona) {
			//Asignando valores de rol
			if(objPersona.getRol().equals(Constants.STR_ONE)) {
				objPersona.setDescRol(Constants.STR_ROL_CLIENTE);
			}else if(objPersona.getRol().equals(Constants.STR_TWO)){
				objPersona.setDescRol(Constants.STR_ROL_MEDICO);
			}else {
				objPersona.setDescRol(Constants.STR_ROL_ADM);
			}
			//Formateando Date a dd/MM/yyyy
			objPersona.setStrFecha(CommonUtils.convertDatetoString(objPersona.getFechaNacimiento(), Constants.STR_DATE_FORMAT));
		}		
		return persona;
	}
	
	public static Page<Mascota> calcularEdad (Page<Mascota> mascota) {
		
		Map<Integer, String> objMpTipoMascota = listaTipoMascota();
		Map<Integer, String> objMpTipoEstado = listaTipoEstado();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");		
		LocalDate ahora = LocalDate.now();
		for (Mascota objMascota: mascota) {
			
			//Calcular edad
			String auxEdad="";
			LocalDate fechaNac = LocalDate.parse(CommonUtils.convertDatetoString(
					objMascota.getMascotaFechaNac(), Constants.STR_DATE_FORMAT), fmt);
			Period periodo = Period.between(fechaNac, ahora);
			
			int auxAnio = periodo.getYears();
			int auxMes = periodo.getMonths();
			int auxDia = periodo.getDays();
			String strAño = auxAnio + " año";
			String strMes = auxMes + " mes";
			String strDia = auxDia + " días";			
			
			if(auxAnio > 1) {
				strAño = auxAnio + " años";
			}
			if(auxMes > 1) {
				strMes = auxMes + " meses";
			}		

			if(periodo.getYears()==0) {
				if(periodo.getMonths()==0) {
					auxEdad = strDia;
				}else {
					auxEdad = strMes + " " + strDia;
				}			
			}else {
				auxEdad = strAño + " " + strMes;
			}
			objMascota.setEdad(auxEdad);
			
			//Asignar estado
			objMascota.setEtiquetaTipo(objMpTipoMascota.get(objMascota.getTipoMascota()));
			//Asignar tipo mascota
			objMascota.setEtiquetaEstado(objMpTipoEstado.get(objMascota.getEstado()));
		}		
		return mascota;
	}
	
	public static List<Mascota> calcularEdad (List<Mascota> mascota) {
		
		Map<Integer, String> objMpTipoMascota = listaTipoMascota();
		Map<Integer, String> objMpTipoEstado = listaTipoEstado();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");		
		LocalDate ahora = LocalDate.now();
		for (Mascota objMascota: mascota) {
			
			//Calcular edad
			String auxEdad="";
			LocalDate fechaNac = LocalDate.parse(CommonUtils.convertDatetoString(
					objMascota.getMascotaFechaNac(), Constants.STR_DATE_FORMAT), fmt);
			Period periodo = Period.between(fechaNac, ahora);
			
			int auxAnio = periodo.getYears();
			int auxMes = periodo.getMonths();
			int auxDia = periodo.getDays();
			String strAño = auxAnio + " año";
			String strMes = auxMes + " mes";
			String strDia = auxDia + " días";			
			
			if(auxAnio > 1) {
				strAño = auxAnio + " años";
			}
			if(auxMes > 1) {
				strMes = auxMes + " meses";
			}		

			if(periodo.getYears()==0) {
				if(periodo.getMonths()==0) {
					auxEdad = strDia;
				}else {
					auxEdad = strMes + " " + strDia;
				}			
			}else {
				auxEdad = strAño + " " + strMes;
			}
			objMascota.setEdad(auxEdad);
			
			//Asignar estado
			objMascota.setEtiquetaTipo(objMpTipoMascota.get(objMascota.getTipoMascota()));
			//Asignar tipo mascota
			objMascota.setEtiquetaEstado(objMpTipoEstado.get(objMascota.getEstado()));
		}		
		return mascota;
	}
	
	public static Map<Integer,String> listaTipoMascota(){
		Map<Integer, String> objMpTipoMascota = new HashMap<Integer, String>();
		objMpTipoMascota.put(1,"Perro");
		objMpTipoMascota.put(2,"Gato");
		objMpTipoMascota.put(3,"Hásmter");
		objMpTipoMascota.put(4,"Conejo");
		objMpTipoMascota.put(5,"Ave");
		objMpTipoMascota.put(6,"Reptil");
		objMpTipoMascota.put(7,"Pez");
		objMpTipoMascota.put(8,"Otro");
		return objMpTipoMascota;
	}
	
	public static Map<Integer,String> listaTipoEstado(){
		Map<Integer, String> objMpTipoEstado = new HashMap<Integer, String>();
		objMpTipoEstado.put(1,"Activo");
		objMpTipoEstado.put(2,"Inactivo");
		return objMpTipoEstado;
	}
}
