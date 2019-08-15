package com.example.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.ResourceUtils;

class NameIndex {
	public String name;
	public int index;

	public NameIndex(String name, int index) {
		super();
		this.name = name;
		this.index = index;
	}

}

public class Parser {

	public List<MesasTotalesAgrup> parseMesasTotalesAgrupacion() {
		List<MesasTotalesAgrup> results = new ArrayList<MesasTotalesAgrup>();
		try {
			Path path = getPath(DemoResultadosApplication.mesas_totales_agrp);

			Stream<String> lines = Files.lines(path);

			List<String> textes = lines.collect(Collectors.toList());
			
			double size = (double)textes.size();
			double count = 0;
			double perc = 0;
			
			for (String string : textes) {
				String[] res = string.split("\\|");
				if (res[0].equals("02") && res[4].equals("000100000000000")) {
					results.add(new MesasTotalesAgrup(res[0], res[1], res[2], res[3], res[4], res[5], res[6]));

				}

				count++;
				 perc = (count / size) * 100;
				System.out.println("Analizado Mesas Totales Agrupacion " + perc);
			}

			lines.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Surgio un error");
		}
		return results;

	}

	// id mesa - Resultado Mesa
	public Map<String, Map<String, ResultadoMesa>> parseMesasTotalesPresidenteListaXMesa() {
		Map<String, Map<String, ResultadoMesa>> results = new HashMap<String, Map<String, ResultadoMesa>>();
		try {
			Path path = getPath(DemoResultadosApplication.mesas_totales_lista_fix);

			Stream<String> lines = Files.lines(path);

			List<String> textes = lines.collect(Collectors.toList());

			double size = (double)textes.size();
			double count = 0;
			double perc = 0;
			for (String string : textes) {
				if (string.equals(textes.get(0)))
					continue;

				String[] res = string.split("\\|");

				// me fijo si ya existe una mesa con ese codigo de mesa (res[3])
				if (!results.containsKey(res[3]))
					results.put(res[3], new HashMap<String, ResultadoMesa>());

				// me fijo si en la mesa con ese codigo, existe la categoria( res[4])

				if (!(results.get(res[3]).containsKey(res[4])))
					results.get(res[3]).put(res[4], new ResultadoMesa(res[0], res[1], res[2], res[3]));

				ResultadoMesa rm = results.get(res[3]).get(res[4]);
				rm.addResultado(res[5], res[7]);

				count++;
				perc = (count / size) * 100;
				System.out.println("Analizado Mesas Totales x mesa" + perc);
			}

			lines.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Surgio un error");
		}
		return results;

	}

	public void replaceCodigoRegionPorNombre(Map<String, String> regiones) {
		try {
			Path path = getPath(DemoResultadosApplication.mesas_totales_lista);

			Stream<String> lines = Files.lines(path);
			List<String> textes = lines.collect(Collectors.toList());

			PrintWriter out = new PrintWriter("mesas_totales_lista_fix.csv");

			double size = (double)textes.size();
			double count = 0;
			double perc = 0;
			String newString = textes.get(0);
			out.println(newString);
			for (String string : textes) {

				if (string.equals(textes.get(0)))
					continue;

				String[] res = string.split("\\|");

				newString = regiones.get(res[0]) + "|" + regiones.get(res[1]) + "|" + regiones.get(res[2]) + "|"
						+ res[3] + "|" + res[4] + "|" + res[5] + "|" + res[6] + "|" + res[7];
				System.out.println(newString);
				count++;
				 perc = ( count /  size) * 100;
				out.println(newString);
				System.out.println("Analizado Escribiendo " + perc);

				// newString += (string + "\n");
			}

			out.close();
			lines.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Surgio un error");
		}

	}

	public List<MesasTotalesLista> parseMesasTotalesLista() {
		List<MesasTotalesLista> results = new ArrayList<MesasTotalesLista>();
		try {
			Path path = getPath(DemoResultadosApplication.mesas_totales_lista_fix);
			Stream<String> lines = Files.lines(path);
			List<String> textes = lines.collect(Collectors.toList());

			double size = textes.size();
			double count = 0;
			double perc = 0;

			for (String string : textes) {
				String[] res = string.split("\\|");
				if (res[4].equals("000100000000000")) {
					results.add(new MesasTotalesLista(res[0], res[1], res[2], res[3], res[4], res[5], res[5], res[7]));

				}

				count++;
				perc = ( count /  size) * 100;
				System.out.println("Analizado Mesas Totales Agrupacion " + perc);
			}

			lines.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Surgio un error");
		}
		return results;

	}

	public List<Region> parseRegion() {
		List<Region> results = new ArrayList<Region>();
		try {
			Path path = getPath(DemoResultadosApplication.descripcion_regiones);
			Stream<String> lines = Files.lines(path);

			List<String> textes = lines.collect(Collectors.toList());

			double size = textes.size();
			double count = 0;
			double perc = 0;
			for (String string : textes) {

				if (string.equals(textes.get(0)))
					continue;
				String[] res = string.split("\\|");
				results.add(new Region(res[0], res[1]));
				count++;
				perc = (count / size) * 100;
				System.out.println("Completado Regiones " + perc);
			}

			lines.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Surgio un error");
		}
		return results;

	}

	public List<Postulante> parsePostulante() {
		List<Postulante> results = new ArrayList<Postulante>();
		try {
			Path path = getPath(DemoResultadosApplication.descripcion_postulaciones);

			Stream<String> lines = Files.lines(path);

			List<String> textes = lines.collect(Collectors.toList());

			double size = (double)textes.size();
			double count = 0;
			double perc = 0;
			for (String string : textes) {
				String[] res = string.split("\\|");
				results.add(new Postulante(res[0], res[1], res[2], res[3], res[4], res[5]));
				count++;
				perc = (count / size) * 100;
				System.out.println("Completado Postulaciones " + perc);
			}

			lines.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Surgio un error");
		}
		return results;

	}

	public Path getPath(String filePath) {
		try {
			File file = ResourceUtils.getFile("classpath:static/" + filePath);
			return Paths.get(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
