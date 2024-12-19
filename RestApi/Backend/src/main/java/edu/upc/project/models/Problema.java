package edu.upc.project.models;

public class Problema {
    String fecha;
    String titulo;
    String mensaje;
    String id;


    public Problema(String fecha, String titulo, String mensaje, String id) {
        this.id = id;
        this.fecha= fecha;
        this.mensaje=mensaje;
        this.titulo= titulo;

    }
    public String getFecha() {
        return fecha;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
