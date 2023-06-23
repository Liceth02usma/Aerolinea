package edu.prog2.model;

import org.json.JSONObject;

public class Pasajero implements IFormatCSV {
    private String identificacion;
    private String nombres;
    private String apellidos;

    /**
     * Constructor vacio
     */
    public Pasajero() {

    }

    public Pasajero(JSONObject json) {
        this(
                json.getString("identifiacion"),
                json.getString("nombre"),
                json.getString("apellido"));
    }

    /**
     * Constructor encargado de copiar los valores dados por el argumento.
     * 
     * @param copia la clase
     */
    public Pasajero(Pasajero copia) {
        this(copia.identificacion, copia.nombres, copia.apellidos);

    }

    /**
     * Cosntructor encargado de asignar el valor de los argumentos a los atributos
     * de clase
     * 
     * @param id       identificacion de la persona
     * @param nombre   nombre de la persona
     * @param apellido apellido de la persona
     */
    public Pasajero(String id, String nombre, String apellido) {
        this.identificacion = id;
        this.nombres = nombre;
        this.apellidos = apellido;

    }

    public Pasajero(String json) {
        this(new JSONObject(json));
    }

    /**
     * Devuelve la identificacion de la persona
     * 
     * @return la identificacion de la persona
     */
    public String getIdentifiacion() {
        return this.identificacion;
    }

    /**
     * Devuelven el nombre de la persona
     * 
     * @return el nombre de la persona
     */
    public String getNombre() {
        return this.nombres;
    }

    /**
     * Devuelven el apellido de la persona
     * 
     * @return el apellido de la persona
     */
    public String getApellido() {
        return this.apellidos;
    }

    /**
     * se le asigna el valor del argumento al atributo identificacion
     * 
     * @param id identificacion
     */
    public void setIdentificacion(String id) {
        this.identificacion = id;
    }

    /**
     * se le asigna el valor del argumento al atributo nombre
     * 
     * @param nombre
     */
    public void setNombres(String nombre) {
        this.nombres = nombre;
    }

    /**
     * se le asigna el valor del argumento al atributo apellido
     * 
     * @param apellido
     */
    public void setApellidos(String apellido) {
        this.apellidos = apellido;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pasajero)) {
            return false;
        }
        Pasajero p = (Pasajero) obj;
        return identificacion.equals(p.identificacion);
    }

    public String toCSV() {
        return String.format("%s;%s;%s%n", identificacion, nombres, apellidos);
    }

    @Override
    public String toString() {
        return String.format("%-10s%-20s%s", identificacion, nombres, apellidos);
    }
}
