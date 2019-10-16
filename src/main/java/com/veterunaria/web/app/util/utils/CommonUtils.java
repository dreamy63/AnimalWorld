package com.veterunaria.web.app.util.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.Page;

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

}
