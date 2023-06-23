package edu.prog2.model;

import org.json.JSONObject;

public class SillaEjecutiva extends Silla {

    private Menu menu;
    private Licor licor;

    public SillaEjecutiva(int fila, char columna, Menu menu, Licor licor, Avion avion) {
        super(fila, columna, avion); // super llama el conctructor parametrizado de la clase Silla
        ubicacion = (columna == 'B' || columna == 'C') ? Ubicacion.PASILLO : Ubicacion.VENTANA;
        this.menu = menu;
        this.licor = licor;
    }

    public SillaEjecutiva() {

    }

    public SillaEjecutiva(JSONObject json) {
        this(
                json.getInt("fila"),
                json.getString("columna").charAt(0),
                json.getEnum(Menu.class, "menu"),
                json.getEnum(Licor.class, "licor"),
                new Avion(json.getJSONObject("avion")));
        setDisponible(json.getBoolean("disponible"));
    }

    public SillaEjecutiva(SillaEjecutiva silla) {
        this(silla.fila, silla.columna, silla.menu, silla.licor, silla.avion);
    }

    public SillaEjecutiva(String json) {
        this(new JSONObject(json));
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Licor getLicor() {
        return licor;
    }

    public void setLicor(Licor licor) {
        this.licor = licor;
    }

    public String toCSV() {

        return String.format("%s;%s;%s%n", super.toCSV().substring(0, super.toCSV().length() - 2), menu, licor);
    }

    @Override
    public String toString() {
        String menu = this.menu.toString().replaceAll("_", " ");
        String licor = this.licor.toString().replaceAll("_", " ");
        return String.format("%-16s%-20s%s", super.toString(), menu, licor); // super to string accede al to string de
                                                                             // la clase Silla
    }

}
