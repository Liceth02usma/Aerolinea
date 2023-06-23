package edu.prog2.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

import edu.prog2.App;
import edu.prog2.helpers.UtilFiles;
import edu.prog2.model.*;

public class SillasService {
    private ArrayList<Silla> sillas;
    private AvionesService aviones;
    private String fileName;

    public SillasService(AvionesService aviones) throws IOException {
        this.aviones = aviones;
        sillas = new ArrayList<>();
        fileName = UtilFiles.FILE_PATH + "sillas";

        if (UtilFiles.fileExists(fileName + ".csv")) {
            loadCSV();
        } else if (UtilFiles.fileExists(fileName + ".json")) {
            loadJSON();
        } else {
            System.out.println("Aún no se ha creado un archivo: " + fileName);
        }
    }

    public boolean add(Silla silla) throws IOException {
        if (contains(silla)) {
            throw new ArrayStoreException(
                    String.format("No agregada, la silla %s ya existe%n", silla.getPosicion()));

        }

        return sillas.add(silla);
    }

    public boolean contains(Silla silla) {
        return sillas.contains(silla);
    }

    public Silla get(int index) {
        return sillas.get(index);
    }

    public Silla get(Silla silla) {
        int index = sillas.indexOf(silla);
        return index > -1 ? sillas.get(index) : null;
    }

    public ArrayList<Silla> getList() {
        return sillas;
    }

    public AvionesService getAviones() {
        return aviones;
    }

    public JSONArray getJSON() throws IOException {
        return new JSONArray(UtilFiles.readText(fileName + ".json"));
    }

    public JSONObject getJSON(int index) {
        return new JSONObject(sillas.get(index));
    }

    public JSONObject getJSON(Silla silla) {
        int index = sillas.indexOf(silla);
        return index > -1 ? getJSON(index) : null;
    }

    public JSONArray aircraftWithNumberSeats() throws Exception {
        JSONArray data = new JSONArray();

        for (Avion a : aviones.getList()) {
            data.put(numberOfSeats(a.getMatricula()));
        }

        return data;
    }

    public JSONObject numberOfSeats(String matricula) throws Exception {
        JSONObject data = new JSONObject();
        JSONObject nSillas = new JSONObject();
        int sillasEjecutivas = 0;
        int sillasEconomicas = 0;
        Avion avion = new Avion(matricula, "");
        avion = aviones.get(avion);
        for (Silla s : sillas) {
            if (avion.equals(s.getAvion())) {
                if (s instanceof SillaEjecutiva) {
                    sillasEjecutivas++;
                } else {
                    sillasEconomicas++;
                }
            }
        }
        nSillas.put("ejecutivas", sillasEjecutivas);
        nSillas.put("economicas", sillasEconomicas);
        data.put("totalSillas", nSillas);
        data.put("matricula", avion.getMatricula());
        data.put("modelo", avion.getModelo());
        return data;
    }

    public ArrayList<Silla> listadoSillas(Avion a) {
        ArrayList<Silla> silla2 = new ArrayList<>();
        for (Silla s : sillas) {
            if (s.getAvion().equals(a)) {
                silla2.add(s);
            }
        }
        return silla2;
    }

    public JSONArray DisponibleVuelo(String params) throws IOException {
        JSONArray sillasJson = new JSONArray();
        JSONObject Vuelojson = UtilFiles.paramsToJson(params);
        System.out.println(Vuelojson.toString(2));
        LocalDateTime fechaHora = LocalDateTime.parse(Vuelojson.getString("fechaHora"));
        System.out.println(fechaHora);
        Trayecto trayecto = new Trayecto(Vuelojson.getString("origen"), Vuelojson.getString("destino"), Duration.ZERO,
                0.0);
        System.out.println(trayecto);
        Avion avion = new Avion(Vuelojson.getString("avion"), "");
        System.out.println(avion);
        Vuelo vuelo = new Vuelo(fechaHora, trayecto, avion);
        System.out.println(vuelo);
        ArrayList<Silla> Listsillas = App.sillasDisponiblesEnVuelo(vuelo);
        sillasJson.put(Listsillas);
        return sillasJson;
    }

    public void removeAvion(String params) throws Exception {
        Avion avion = new Avion(params, "");
        ArrayList<Silla> s = listadoSillas(avion);

        for (Silla silla : s) {
            if (silla.getAvion().equals(avion)) {
                remove("fila=" + silla.getFila() + "&columna=" + silla.getColumna() + "&avion="
                        + silla.getAvion().getMatricula());
            }
        }

    }

    public void remove(String params) throws Exception {
        JSONObject json = get(params);
        String columna = json.getString("posicion");
        columna = columna.substring(columna.length() - 1, columna.length());
        json.put("columna", columna);
        boolean ok = false;
        Silla silla = new Silla(json);
        for (Avion a : aviones.getList()) {
            if (a.equals(silla.getAvion())) {
                ok = true;
                break;
            }
        }
        if (ok) {
            throw new Exception(String.format(
                    "No se eliminó la silla porque esta referenciada en multiples clases", silla));
        }

        if (!sillas.remove(silla)) {
            throw new Exception(String.format(
                    "No se encontró la silla"));
        }

        UtilFiles.writeData(sillas, fileName);
    }

    public JSONObject set(JSONObject json) throws IOException {
        Silla silla = new Silla(json.getInt("fila"), json.getString("columna").charAt(0),
                new Avion(json.getString("avion"), ""));
        // referenciar la instancia a actualizar
        silla = get(silla);
        silla.setDisponible(json.getBoolean("disponible"));
        if (json.has("menu")) {
            SillaEjecutiva aux = (SillaEjecutiva) silla;
            aux.setMenu(Enum.valueOf(Menu.class, json.getString("menu")));
            aux.setLicor(Enum.valueOf(Licor.class, json.getString("licor")));
            silla = aux;
        }
        UtilFiles.writeData(sillas, fileName);
        return new JSONObject(silla);
    }

    public void update() throws IOException {
        sillas = new ArrayList<>();
        loadCSV();
        UtilFiles.writeJSON(sillas, fileName + ".json");
    }

    public JSONObject get(String paramsVuelo) throws IOException {

        JSONObject json = UtilFiles.paramsToJson(paramsVuelo);

        Avion avion = new Avion(json.getString("avion"), "");
        int fila = json.getInt("fila");
        char columna = (json.getString("columna")).charAt(0);
        Silla silla = get(new Silla(fila, columna, avion));
        return getJSON(silla);
    }

    public void create(Avion avion, int ejecutivas, int economicas) throws IOException {
        create(avion, ejecutivas, new char[] { 'A', 'B', 'C', 'D' }, 1);
        create(avion, economicas, new char[] { 'A', 'B', 'C', 'D', 'E', 'F' },
                ejecutivas / 4 + 1);
        UtilFiles.writeData(sillas, fileName);
        System.out.println("Creadas las sillas del avión " + avion.getMatricula());
    }

    public void create(String matriculaAvion, int ejecutivas, int economicas) throws IOException {
        Avion avion = aviones.get(new Avion(matriculaAvion, ""));
        create(avion, ejecutivas, economicas);
    }

    public void create(Avion avion, int totalSillas, char[] columnas, int inicio) throws IOException {
        if (columnas.length == 4) {
            if (totalSillas % 4 == 0) {
                for (int i = inicio; i <= (totalSillas / 4); i++) {
                    for (int k = 0; k < columnas.length; k++) {
                        add(new SillaEjecutiva(i, columnas[k], Menu.INDEFINIDO, Licor.NINGUNO, avion));
                    }
                }
            } else {
                String mensaje = "El número de sillas no es múltiplo de 4";
                throw new IndexOutOfBoundsException(mensaje);
            }

        }
        if (columnas.length == 6) {
            if (totalSillas % 6 == 0) {
                for (int i = inicio; i < (totalSillas / 6) + inicio; i++) {
                    for (int k = 0; k < columnas.length; k++) {
                        add(new Silla(i, columnas[k], avion));
                    }
                }
            } else {
                String mensaje = "El número de sillas no es múltiplo de 6";
                throw new IndexOutOfBoundsException(mensaje);
            }
        }
    }

    public ArrayList<Silla> loadJSON() throws IOException {
        sillas = new ArrayList<>();
        String data = UtilFiles.readText(fileName + ".json");
        JSONArray jsonArr = new JSONArray(data);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            boolean aux = jsonObj.has("menu");
            if (aux) {
                sillas.add(new SillaEjecutiva(jsonObj));
            } else {
                sillas.add(new Silla(jsonObj));
            }
        }

        return sillas;
    }

    private void loadCSV() throws IOException {
        String linea;
        String matricula;
        int fila;
        char columna;
        try (BufferedReader archivo = Files.newBufferedReader(Paths.get(fileName + ".csv"))) {
            while ((linea = archivo.readLine()) != null) {
                String data[] = linea.split(";");

                matricula = data[0];
                fila = Integer.parseInt(data[1]);
                columna = data[2].charAt(0);
                Avion avion = aviones.get(new Avion(matricula, ""));

                if (data.length == 7) { // ejecutivas
                    Menu menu = Menu.valueOf(data[5]);
                    Licor licor = Licor.valueOf(data[6]);
                    SillaEjecutiva silla = new SillaEjecutiva(fila, columna, menu, licor, avion);
                    add(silla);

                } else if (data.length == 5) { // económicas
                    Silla silla = new Silla(fila, columna, avion);
                    add(silla);
                } else { // error
                    throw new IOException("Se esperaban 5 o 7 datos por línea");
                }

            }
        }
    }
}
