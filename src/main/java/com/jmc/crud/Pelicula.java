package com.jmc.crud;

public class Pelicula {
    private int id;
    private String Nombre;
    private String Genero;
    private int ano;

    public Pelicula(int id, String nombre, String genero, int ano) {
        this.id = id;
        Nombre = nombre;
        Genero = genero;
        this.ano = ano;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getGenero() {
        return Genero;
    }

    public int getAno() {
        return ano;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
