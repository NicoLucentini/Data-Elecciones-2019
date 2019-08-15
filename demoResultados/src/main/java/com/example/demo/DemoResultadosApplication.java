package com.example.demo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoResultadosApplication {

	static List<Region> regiones = new ArrayList<Region>();
	static List<Postulante> postulantes = new ArrayList<Postulante>();
	static List<MesasTotalesLista> mesas = new ArrayList<MesasTotalesLista>();

	
	
	
	static String descripcion_postulaciones = "descripcion_postulaciones.dsv";
	static String descripcion_regiones = "descripcion_regiones.dsv";
	static String mesas_totales_agrp= "mesas_totales_agrp_politica.csv";
	static String mesas_totales_lista_fix = "mesas_totales_lista_fix.csv";
	static String mesas_totales_lista = "mesas_totales_lista.csv";
	static String mesas_totales = "mesas_totales.csv";

	
	static Parser parser;
	public static void main(String[] args) {
		SpringApplication.run(DemoResultadosApplication.class, args);

		parser = new Parser();

		
		loadPostulantes();
		loadRegiones();
		
		calcularMesasIrregulares();
		
		
	}
	public static void loadRegiones() {
		regiones = parser.parseRegion();
	}
	public static void loadPostulantes() {
		postulantes = parser.parsePostulante();
	}
	public static void calcularMesasIrregulares() {

		Map<String, Map<String, ResultadoMesa>> resultadosMesas = parser.parseMesasTotalesPresidenteListaXMesa();

		try {
			PrintWriter pwMacri = new PrintWriter("MESASIRREGULARES_CAMBIO_SINVOTOS.csv");
			PrintWriter pwTodos = new PrintWriter("MESASIRREGULARES_TODOS_SINVOTOS"
					+ ""
					+ ""
					+ ".csv");

			PrintWriter pwAll = new PrintWriter("MESASIRREGULARES_TODOSYCAMBIO.csv");
			int sumMacri = 0;
			int sumTodos = 0;
			double count = 0;
			double size = (double) resultadosMesas.size();
			double perc = 0;
			for (Map.Entry<String, Map<String, ResultadoMesa>> entry : resultadosMesas.entrySet()) {

				if (entry.getValue().get("000100000000000") != null) {
					ResultadoMesa rm = entry.getValue().get("000100000000000");

					if ((rm.partidos.get("135") == 0 ||  rm.partidos.get("135") == 1 )&& rm.partidos.get("136") > 0) {
						writeToFile(rm.toString3(), pwMacri);
						writeToFile(rm.toString3() + "|Cambio" , pwAll);
						sumMacri++;
					}
					if ((rm.partidos.get("136") == 0 ||  rm.partidos.get("135") == 1 ) && rm.partidos.get("135") > 0) {
						writeToFile(rm.toString3(), pwTodos);
						writeToFile(rm.toString3() + "|Todos" , pwAll);
						sumTodos++;
					}
					
				}

				count++;
				perc = (count / size) * 100;
				System.out.println("Mesas Analizadas " + perc + " %");
				
			}
			pwMacri.close();
			pwTodos.close();
			pwAll.close();
			System.out.println("Macri " + sumMacri);
			System.out.println("Todos " + sumTodos);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void writeToFile(String line, PrintWriter pw) {
		pw.println(line);

	}

	public static String getAgrupacion(String codigo) {
		for (Postulante post : postulantes) {
			if (post.codigo_agrupacion.equals(codigo))
				return post.nombre_agrupacion;
		}
		return "error";

	}

	public static String getCategoria(String codigo) {
		for (Postulante post : postulantes) {
			if (post.codigo_categoria.equals(codigo))
				return post.nombre_categoria;
		}
		return "error";
	}

}
