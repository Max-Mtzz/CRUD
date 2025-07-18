package com.jmc.crud;

public class Pelicula {
    private int id;

    public Pelicula(int id, String nombre, String genero, int ano) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.ano = ano;
    }

    private String nombre;
    private String genero;
    private int ano;
}
