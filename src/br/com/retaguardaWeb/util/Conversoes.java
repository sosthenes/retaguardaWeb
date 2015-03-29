package br.com.retaguardaWeb.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Conversoes {

	DecimalFormat qtdeParser = new DecimalFormat( "0.00");
	
	public SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");  
	
	public String converteDoubleToString(double valor){
		return qtdeParser.format(valor);
	}
	
	
	public String incrementaMesADataString(Date data){
		Calendar calendar =(Calendar) DateToCalendar(data).clone();
		calendar.add(Calendar.MONTH, 1);
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String data_date = formatter.format(calendar.getTime()); 
		return data_date;
	}
	
	public Date incrementaMesADataDate(Date data){
		Calendar calendar =(Calendar) DateToCalendar(data).clone();
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime(); 
	}
	
	public Date incrementaDiasADataDate(Date data, int qtd){
		Calendar calendar =(Calendar) DateToCalendar(data).clone();
		calendar.add(Calendar.DAY_OF_MONTH, qtd);
		return calendar.getTime(); 
	}
	
	
	public String recuperaMesAnoExtenso(Date data){
		Locale local = new Locale("pt","BR");  
		//DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",local);   
		DateFormat dateFormat = new SimpleDateFormat("MMMM 'de' yyyy",local);   
		return dateFormat.format(data);
	}
	
	
	public static Calendar DateToCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
		}


	public Date incrementaMesDataDate(Date dataCompra, int i) {
		
		Calendar calendar =(Calendar) DateToCalendar(dataCompra).clone();
		calendar.add(Calendar.MONTH, (i/30-1));
		return calendar.getTime(); 
	}


	public Date incrementaAnoADataDate(Date dataCompra, int i) {
		Calendar calendar =(Calendar) DateToCalendar(dataCompra).clone();
		calendar.add(Calendar.YEAR, (i/365-1));
		return calendar.getTime(); 
	}
	
}
