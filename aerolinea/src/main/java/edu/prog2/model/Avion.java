package edu.prog2.model;

import org.json.JSONObject;

public class Avion implements IFormatCSV {
    private String matricula;
    private String modelo;

    /**
     * Constructor vacio
     */
    public Avion() {

    }

    public Avion(JSONObject json) {
        this(
                json.getString("matricula"),
                json.getString("modelo"));

    }

    /**
     * Constructor encargado de copiar los valores dados por el argumento.
     * 
     * @param copia la clase
     */
    public Avion(Avion copia) {
        this(copia.matricula, copia.modelo);

    }

    /**
     * Cosntructor encargado de asignar el valor de los argumentos a los atributos
     * de clase
     * 
     * @param matricula matricula del avion
     * @param modelo    modelo del avion
     */
    public Avion(String matricula, String modelo) {
        this.matricula = matricula;
        this.modelo = modelo;

    }

    public Avion(String json) {
        this(new JSONObject(json));
    }

    /**
     * Devuelve la matricula del avion
     * 
     * @return la matricula
     */
    public String getMatricula() {
        return this.matricula;
    }

    /**
     * Devuelve el modelo del avion
     * 
     * @return el modelo
     */
    public String getModelo() {
        return this.modelo;
    }

    /**
     * se le asigna el valor del argumento al atributo matricula
     * 
     * @param matricula
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * se le asigna el valor del argumento al atributo modelo
     * 
     * @param modelo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Avion)) {
            return false;
        }
        Avion p = (Avion) obj;
        return matricula.equals(p.matricula);
    }

    @Override
    public String toString() {
        return String.format("%-14s%-20s", matricula, modelo);

    }

    @Override
    public String toCSV() {
        return String.format("%s;%s%n", matricula, modelo);
    }

}
