package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class ResultadoMesa {

	// codigo partido

	Map<String, Integer> partidos = new HashMap<String, Integer>();

	public String distrito;
	public String seccion;
	public String circuito;
	public String mesa;

	public ResultadoMesa() {

	}

	public ResultadoMesa(String distrito, String seccion, String circuito, String mesa) {
		super();
		this.distrito = distrito;
		this.seccion = seccion;
		this.circuito = circuito;
		this.mesa = mesa;
	}

	@Override
	public String toString() {
		String res = "[Distrito " + distrito + ", Seccion " + seccion + ", Circuito: " + circuito + "[";

		for (Map.Entry<String, Integer> entry : partidos.entrySet()) {
			res += DemoResultadosApplication.getAgrupacion(entry.getKey()) + ": " + entry.getValue().toString() + ", ";
		}

		res += "]";
		return res;
	}

	public String toString2() {
		return distritoToString() + "|" + seccionToString() + "|" + circuitoToString() + "|" + mesa;
	}
	public String toString3() {
		return distrito+ "|" + seccion + "|" + circuito+ "|" + mesa;
	}
	public String distritoToString() {
		return distrito + ", Argentina";
	}

	public String seccionToString() {
		return seccion + ", " + distritoToString();
	}

	public String circuitoToString() {
		return circuito + ", " + seccionToString();
	}

	public void addResultado(String partido, String votos) {
		if (partidos.containsKey(partido))
			partidos.replace(partido, partidos.get(partido) + Integer.parseInt(votos));
		else
			partidos.put(partido, Integer.parseInt(votos));
	}

}
