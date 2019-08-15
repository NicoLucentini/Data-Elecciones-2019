package com.example.demo;

public class MesasTotalesLista {
	public String codigo_distrito;
	public String codigo_seccion;
	public String codigo_circuito;
	public String codigo_mesa;
	public String codigo_categoria;
	public String codigo_agrupacion;
	public String codigo_lista;
	public String votos_agrupacion;
	public MesasTotalesLista(String codigo_distrito, String codigo_seccion, String codigo_circuito, String codigo_mesa,
			String codigo_categoria, String codigo_agrupacion,String codigo_lista, String votos_agrupacion) {
		super();
		this.codigo_distrito = codigo_distrito;
		this.codigo_seccion = codigo_seccion;
		this.codigo_circuito = codigo_circuito;
		this.codigo_mesa = codigo_mesa;
		this.codigo_categoria = codigo_categoria;
		this.codigo_agrupacion = codigo_agrupacion;
		this.codigo_lista = codigo_lista;
		this.votos_agrupacion = votos_agrupacion;
	}

}
