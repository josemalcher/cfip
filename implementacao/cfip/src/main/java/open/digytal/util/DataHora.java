package open.digytal.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DataHora implements DataHoraConstants {

	public static int get(Date data, int atributo, Locale localizacao) {
		Calendar calendar = Calendar.getInstance(localizacao);
		calendar.setTime(data);
		int get = calendar.get(atributo);
		get=atributo==MES?get+1:get;
		return get;
	}
	public static int get(Date data, int atributo) {
		return get(data, atributo, LOCALIZACAO);
	}
	public static int dia(Date data) {
		return get(data,DIA);
	}
	public static int mes(Date data) {
		return get(data,MES);
	}
	public static int ano(Date data) {
		return get(data,ANO);
	}
	public static int hora(Date data) {
		return get(data,HORA);
	}
	public static int minuto(Date data) {
		return get(data,MINUTO);
	}
	public static int segundo(Date data) {
		return get(data,SEGUNDO);
	}
	public static int periodo(Date data) {
		return Integer.valueOf(Formatador.formatar(ano(data),"0000") + Formatador.formatar(mes(data),"00"));
	}
	public static void main(String[] args) {
		GregorianCalendar gc = new GregorianCalendar(1984,5,30,6,37,25);
		gc.set(AM_PM, Calendar.AM);
		Date data = gc.getTime();
		String format = "%-15s:%d";
		System.out.println(data);

		System.out.println("DATA");
		System.out.println(String.format(format,"DIA",dia(data)) );
		System.out.println(String.format(format,"MES",mes(data)) );
		System.out.println(String.format(format,"ANO",ano(data)) );
		System.out.println(String.format(format,"PERIODO",periodo(data)) );

		System.out.println("HORA");
		System.out.println(String.format(format,"HORA",hora(data)) );
		System.out.println(String.format(format,"MINUTO",minuto(data)) );
		System.out.println(String.format(format,"SEGUNDO",segundo(data)) );

	}
	
}
