package br.com.gbessa.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

    public static String decodeParam(String param) {
	try {
	    return URLDecoder.decode(param, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    return "";
	}
    }
    
    public static List<Integer> decodeListInteger(String s) {
	
	List<Integer> lista = new ArrayList<Integer>();
	
	String[] vet = s.split(",");
	for (String val : vet) {
	    lista.add(Integer.parseInt(val));
	}
	
	return lista;
    }
    
}
