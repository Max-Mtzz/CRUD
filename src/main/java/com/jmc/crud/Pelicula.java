package com.jmc.crud;

public class Pelicula {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {}

    public Pelicula(int id, String nombre, String genero, int ano, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.genero = genero;
        this.ano = ano;
        this.estado = estado;
    }

    private String nombre;
    private String genero;
    private int ano;
    private String estado;
}
