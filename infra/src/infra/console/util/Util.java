package infra.console.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.util.Scanner;

public class Util {
	public static String lerString(String rotulo, int minimo, int maximo){
		Scanner s = new Scanner(System.in);
		String str = "";
		boolean invalida = true;
		do {
			System.out.print(rotulo);				
			str = s.nextLine();
			invalida = str.length() > maximo || str.length() < minimo; 
			if (invalida)
				System.out.println("O tamanho maximo permitido eh de " + maximo + " caracteres e o m�nimo de "+minimo+".");
		} while (invalida);
		return str;
	}

	public static String lerStringOpt(String rotulo, int minimo, int maximo){
		Scanner s = new Scanner(System.in);
		String str = "";
		boolean invalida = true;
		do {
			System.out.print(rotulo);
			str = s.nextLine();
			invalida = str.length() > maximo || str.length() < minimo;
			if(str.length() == 0)
				return null;

			if (invalida)
				System.out.println("O tamanho maximo permitido eh de " + maximo + " caracteres e o m�nimo de "+minimo+".");


		} while (invalida);
		return str;
	}

	public static Integer lerInteiro(String rotulo, int minimo, int maximo){
		Scanner s = new Scanner(System.in);
		int opcao = 0;
		boolean invalida = true;
		do {
			System.out.println(rotulo);
			try {
				opcao = Integer.parseInt(s.nextLine());
				invalida = opcao < minimo || opcao > maximo;
				if (invalida)
					System.out.println("Valor inv�lido!");
			} catch (NumberFormatException e) {
				System.out.println("Valor inv�lido!");
				invalida = true;
				opcao = 0;
			}
		} while (invalida);
		return opcao;		
	}

	public static Double lerDouble(String rotulo, int minimo, int maximo){
		Scanner s = new Scanner(System.in);
		double opcao = .0;
		boolean invalida = true;
		do {
			System.out.println(rotulo);
			try {
				opcao = Double.parseDouble(s.nextLine());
				invalida = opcao < minimo || opcao > maximo;
				if (invalida)
					System.out.println("Valor inv�lido!");
			} catch (NumberFormatException e) {
				System.out.println("Valor inv�lido!");
				invalida = true;
				opcao = 0;
			}
		} while (invalida);
		return opcao;
	}


	private static String lerSouN() {
		Scanner s = new Scanner(System.in);
		String str = "";
		boolean invalida = true;
		do {
			str = s.nextLine();
			invalida = !("N".equals(str.toUpperCase()) || "S".equals(str.toUpperCase())); 
			if (invalida)
				System.out.println("Digite (S)im ou (N)ao para continuar.");
		} while (invalida);
		return str.toUpperCase();
	}

	public static boolean confirma(String titulo) {
		System.out.println("Confirma��o - " + titulo+ ": [s/n] ?");
		return "S".equals(Util.lerSouN());
	}
	public static void main(String[] args) {
	    
	      Date data = new Date(System.currentTimeMillis()); // Pega a data atual
	      SimpleDateFormat formatarDate = new SimpleDateFormat("dd-MM-yyyy"); // Cria um formatador de datas
	      System.out.println(formatarDate.format(data)); // Mostra a data
	      data.setDate(data.getDate() + 40); // 7 dias a mais
	      System.out.println(formatarDate.format(data)); // Mostra a data

	}

	static Date current_date;

	static void setCurrent_date(Date current_date){
		Util.current_date = current_date;
	}

	public static Date getCurrent_date() {
		if (current_date == null)
			current_date = new Date(new java.util.Date().getTime());
		return current_date;
	}

	public static void nextTime(int days){
		;
		Calendar c = Calendar.getInstance();
		c.setTime(current_date);
		c.add(Calendar.DATE, days);
		java.util.Date tmp = c.getTime();
		current_date = new Date(tmp.getTime());
	}

	public static Date nowPlusDays(int days){
		;
		Calendar c = Calendar.getInstance();
		c.setTime(current_date);
		c.add(Calendar.DATE, days);
		java.util.Date tmp = c.getTime();
		return new Date(tmp.getTime());
	}

	public static Date PlusDays(Date d, int days){
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, days);
		java.util.Date tmp = c.getTime();
		return new Date(tmp.getTime());
	}

	public static long diffDays(Date a, Date b){
	return ChronoUnit.DAYS.between(LocalDate.parse(b.toString()), LocalDate.parse(a.toString()));

	}

	public static java.sql.Date lerDataSQL(String rotulo) {
		System.out.println(rotulo);
		int dia = Util.lerInteiro("Dia", 1, 22);
		int mes = Util.lerInteiro("Mes", 1, 12);
		int ano = Util.lerInteiro("Ano", 2000, 2100);

		LocalDate localDate = LocalDate.of(ano, mes, dia);

		return Date.valueOf(localDate);

	}
}
