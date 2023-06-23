package edu.prog2.services;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.prog2.helpers.UtilFiles;
import edu.prog2.model.*;

public class ReservasVuelosService {
    private ArrayList<ReservaVuelo> reservasVuelos;
    private ReservasService reservas;//
    private VuelosService vuelos; //
    private SillasService sillas; //
    private String fileName;
    static PasajerosService pasajeros;//
    static TrayectosService trayectos; //
    static AvionesService aviones;

    public ReservasVuelosService(ReservasService reservas, VuelosService vuelos, SillasService sillas)
            throws IOException {
        this.reservas = reservas;
        this.vuelos = vuelos;
        this.sillas = sillas; /// duda (:
        reservasVuelos = new ArrayList<>();
        fileName = UtilFiles.FILE_PATH + "reservasVuelos";

        if (UtilFiles.fileExists(fileName + ".csv")) {
            loadCSV();
        } else if (UtilFiles.fileExists(fileName + ".json")) {
            loadJSON();
        } else {
            System.out.println("Aún no se ha creado un archivo: " + fileName);
        }
    }

    public boolean add(ReservaVuelo reservaVuelo) throws IOException {
        if (contains(reservaVuelo)) {
            throw new ArrayStoreException(
                    String.format("La reserva de vuelo ya existe"));
        }
        boolean ok = reservasVuelos.add(reservaVuelo);
        UtilFiles.writeData(reservasVuelos, fileName);
        return ok;
    }

    public ArrayList<ReservaVuelo> loadJSON() throws IOException {
        reservasVuelos = new ArrayList<>();
        String data = UtilFiles.readText(fileName + ".json");
        JSONArray jsonArr = new JSONArray(data);

        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            reservasVuelos.add(new ReservaVuelo(jsonObj));
        }

        return reservasVuelos;
    }

    public void update() throws IOException {
        reservasVuelos = new ArrayList<>();
        loadCSV();
        UtilFiles.writeJSON(reservasVuelos, fileName + ".json");
    }

    public boolean contains(ReservaVuelo reservaVuelo) {
        return reservasVuelos.contains(reservaVuelo);
    }

    public ReservasService getReservas() {
        return reservas;
    }

    public SillasService getSillas() {
        return sillas;
    }

    public TrayectosService getTrayectos() {
        return trayectos;
    }

    public AvionesService getAviones() {
        return aviones;
    }

    public ReservaVuelo get(ReservaVuelo reservaVuelo) {
        int index = reservasVuelos.indexOf(reservaVuelo);
        return index > -1 ? reservasVuelos.get(index) : null;
    }

    public PasajerosService getPasajeros() {
        return pasajeros;
    }

    public VuelosService getVuelos() {
        return vuelos;
    }

    public ReservaVuelo get(int index) {
        return reservasVuelos.get(index);
    }

    public ArrayList<ReservaVuelo> getList() {
        return reservasVuelos;
    }

    public JSONArray getJSON() throws IOException {
        return new JSONArray(UtilFiles.readText(fileName + ".json"));
    }

    public JSONObject getJSON(int index) {
        return new JSONObject(reservasVuelos.get(index));
    }

    public JSONObject getJSON(ReservaVuelo reservaVuelo) {
        int index = reservasVuelos.indexOf(reservaVuelo);
        return index > -1 ? getJSON(index) : null;
    }

    public void remove(String params) throws Exception {
        JSONObject json = get(params);
        String columna = json.getJSONObject("silla").getString("posicion");
        columna = columna.substring(columna.length() - 1, columna.length());
        json.getJSONObject("silla").put("columna", columna);
        ReservaVuelo reservaVuelo = get(new ReservaVuelo(json));
        System.out.println(reservaVuelo);
        int n = 0;
        for (ReservaVuelo rv : reservasVuelos) {
            if (rv.getReserva().equals(reservaVuelo.getReserva())) {
                n++;
            }
        }

        if (!reservasVuelos.remove(reservaVuelo)) {
            throw new Exception(String.format("No se encontró la reserva %s", reservaVuelo));
        }
        if (n == 1) {
            reservas.getList().remove(reservaVuelo.getReserva());
        }

        UtilFiles.writeData(reservasVuelos, fileName);
        UtilFiles.writeData(reservas.getList(), UtilFiles.FILE_PATH + "reservas");
    }

    public JSONObject get(String paramsVuelo) throws Exception {
        JSONObject json = UtilFiles.paramsToJson(paramsVuelo);

        LocalDateTime fechaHoraReserva = LocalDateTime.parse(json.getString("fechaHoraReserva"));
        Reserva reserva = new Reserva(fechaHoraReserva, new Pasajero(json.getString("pasajero"), "", ""));
        reserva = reservas.get(reserva);
        String origen = json.getString("origen");
        Avion avion = new Avion(json.getString("avion"), "");
        LocalDateTime fechaHoraVuelo = LocalDateTime.parse(json.getString("fechaHoraVuelo"));
        Vuelo vuelo = vuelos.get(
                new Vuelo(fechaHoraVuelo, new Trayecto(origen, json.getString("destino"), Duration.ZERO, 0.0), avion));

        int fila = json.getInt("fila");
        char columna = (json.getString("columna")).charAt(0);
        Silla silla = sillas.get(new Silla(fila, columna, avion));
        ReservaVuelo reservaVuelo = get(new ReservaVuelo(reserva, vuelo, silla, true));
        if (reservaVuelo == null) {
            throw new Exception(String.format("No se encontró la reserva %s", reservaVuelo));
        }
        return getJSON(reservaVuelo);
    }

    public void add(JSONObject json) throws IOException {
        LocalDateTime fechaHoraReserva = LocalDateTime.parse(json.getString("fechaHoraReserva"));
        Reserva reserva = new Reserva(fechaHoraReserva, new Pasajero(json.getString("pasajero"), "", ""));
        reserva = reservas.get(reserva);
        String origen = json.getString("origen");
        Avion avion = new Avion(json.getString("avion"), "");
        LocalDateTime fechaHoraVuelo = LocalDateTime.parse(json.getString("fechaHoraVuelo"));
        Vuelo vuelo = vuelos.get(
                new Vuelo(fechaHoraVuelo, new Trayecto(origen, json.getString("destino"), Duration.ZERO, 0.0), avion));

        int fila = json.getInt("fila");
        char columna = (json.getString("columna")).charAt(0);
        Silla silla = new Silla(fila, columna, avion);

        if (json.has("menu")) {
            SillaEjecutiva aux = new SillaEjecutiva(fila, columna, null, null, avion);
            aux.setMenu(Enum.valueOf(Menu.class, json.getString("menu")));
            aux.setLicor(Enum.valueOf(Licor.class, json.getString("licor")));
            ReservaVuelo reservaVuelo = new ReservaVuelo(reserva, vuelo, aux, false);
            add(reservaVuelo);
        } else {
            ReservaVuelo reservaVuelo = new ReservaVuelo(reserva, vuelo, silla, false);
            add(reservaVuelo);
        }
    }

    public JSONObject set(String params, JSONObject body) throws Exception {

        JSONObject json = get(params);// crear un JSONObject object a partir de params
        String columna = json.getJSONObject("silla").getString("posicion");
        columna = columna.substring(columna.length() - 1, columna.length());
        json.getJSONObject("silla").put("columna", columna);
        ReservaVuelo reservaVuelo = get(new ReservaVuelo(json));
        int nSillas = 0;
        int oSillas = 0;
        int index = reservasVuelos.indexOf(reservaVuelo);
        if (body.has("origen") && body.has("destino")) {
            Vuelo vuelo = vuelos.get(new Vuelo(LocalDateTime.parse(body.getString("fechaHoraVuelo")),
                    new Trayecto(body.getString("origen"), body.getString("destino"), null, 0.0),
                    new Avion(body.getString("avion"), "")));
            vuelo = vuelos.get(vuelo);
            for (Silla s : sillas.getList()) {
                if (s.getAvion().equals(vuelo.getAvion())) {
                    nSillas++;
                }
            }
            for (ReservaVuelo rv : reservasVuelos) {
                if (rv.getVuelo().equals(vuelo)) {
                    oSillas++;
                }
            } /// cambiar esto ->
            if (vuelo.getCancelado() || nSillas == oSillas || vuelo.getFechaHora().isBefore(LocalDateTime.now())) {
                throw new Exception(String.format("No es posible hacer el cambio"));
            }

            if (body.has("fila") && body.has("columna")) {
                Silla silla = new Silla(body.getInt("fila"), body.getString("columna").charAt(0),
                        new Avion(body.getString("avion"), ""));
                if ((isFreeSilla(silla))) {
                    reservaVuelo.getSilla().setDisponible(true);
                    silla = sillas.get(silla);

                    silla.setDisponible(false);
                    if (body.has("menu")) {
                        SillaEjecutiva aux = (SillaEjecutiva) silla;
                        aux.setMenu(body.getEnum(Menu.class, "menu"));
                        aux.setLicor(body.getEnum(Licor.class, "licor"));
                        reservaVuelo.setSilla(aux);
                        reservaVuelo.setVuelo(vuelo);
                    } else {
                        reservaVuelo.setSilla(silla);
                        reservaVuelo.setVuelo(vuelo);
                    }

                } else {
                    throw new Exception(String.format("la silla esta referenciada en otras clases"));
                }
            } else if (!(body.has("fila") && body.has("columna"))) {
                if (nSillas != oSillas) {
                    for (Silla s : sillas.getList()) {
                        if (vuelo.getAvion().equals(s.getAvion())) {
                            if ((reservaVuelo.getSilla() instanceof SillaEjecutiva) && (s instanceof SillaEjecutiva)) {
                                SillaEjecutiva aux = (SillaEjecutiva) s;
                                if ((isFreeSilla(aux))) {
                                    reservaVuelo.getSilla().setDisponible(true);
                                    reservaVuelo.setSilla(aux);
                                    reservaVuelo.getSilla().setDisponible(false);
                                    reservaVuelo.setVuelo(vuelo);
                                }
                            } else if ((reservaVuelo.getSilla() instanceof Silla) && (s instanceof Silla)) {
                                if ((isFreeSilla(s))) {
                                    reservaVuelo.getSilla().setDisponible(true);
                                    reservaVuelo.setSilla(s);
                                    reservaVuelo.getSilla().setDisponible(false);
                                    reservaVuelo.setVuelo(vuelo);
                                }
                            }
                        }
                    }
                } else {
                    throw new Exception(String.format("Todas las sillas del vuelo estan ocupadas"));
                }

            }
        } else {
            if (body.has("fila") && body.has("columna")) {
                Silla silla = new Silla(body.getInt("fila"), body.getString("columna").charAt(0),
                        reservaVuelo.getVuelo().getAvion());
                if ((isFreeSilla(silla))) {
                    reservaVuelo.getSilla().setDisponible(true);
                    silla = sillas.get(silla);

                    silla.setDisponible(false);
                    if (silla instanceof SillaEjecutiva) {
                        SillaEjecutiva aux = (SillaEjecutiva) silla;
                        if (body.has("menu")) {
                            aux.setMenu(body.getEnum(Menu.class, "menu"));
                            aux.setLicor(body.getEnum(Licor.class, "licor"));
                        } else {
                            aux.setLicor(Licor.NINGUNO);
                            aux.setMenu(Menu.INDEFINIDO);
                        }
                        reservaVuelo.setSilla(aux);
                    } else {
                        reservaVuelo.setSilla(silla);
                    }
                } else {
                    throw new Exception(String.format("No hay sillas disponibles"));
                }
            } else {
                throw new Exception(String.format("No se enviaron los datos de fila y columna"));
            }
        }

        reservasVuelos.set(index, reservaVuelo);
        UtilFiles.writeData(reservasVuelos, fileName);
        return new JSONObject(reservaVuelo);

    }

    private boolean isFreeSilla(Silla aux) {
        for (ReservaVuelo rv : reservasVuelos) {
            if (rv.getSilla().equals(aux)) {
                return false;
            }
        }
        return true;
    }

    private void loadCSV() throws IOException {
        pasajeros = new PasajerosService();
        trayectos = new TrayectosService();
        aviones = new AvionesService();

        String text = UtilFiles.readText(fileName + ".csv");

        try (Scanner sc = new Scanner(text).useDelimiter(";|[\n]+|[\r\n]+")) {
            while (sc.hasNext()) {
                LocalDateTime fechaHoraReserva = LocalDateTime.parse(sc.next());
                Pasajero pasajero = pasajeros.get(new Pasajero(sc.next(), "", ""));
                Reserva reserva = reservas.get(new Reserva(fechaHoraReserva, pasajero));
                LocalDateTime fechaHoraVuelo = LocalDateTime.parse(sc.next());
                String origen = sc.next();
                String destino = sc.next();
                Trayecto trayecto = trayectos.get(new Trayecto(origen, destino, null, 0.0));
                String matricula = sc.next();
                Avion avion = aviones.get(new Avion(matricula, ""));
                Vuelo vuelo = vuelos.get(new Vuelo(fechaHoraVuelo, trayecto, avion));
                int fila = Integer.parseInt(sc.next());
                char columna = (sc.next()).charAt(0);
                Silla silla = sillas.get(new Silla(fila, columna, avion));
                boolean disponible = sc.nextBoolean();
                boolean checkIn = sc.nextBoolean();

                if (silla instanceof SillaEjecutiva) {
                    SillaEjecutiva silla2 = (SillaEjecutiva) silla;
                    String m = sc.next();
                    System.out.println(m);
                    Menu menu = Enum.valueOf(Menu.class, m);
                    Licor licor = Licor.valueOf(sc.next());
                    silla2.setMenu(menu);
                    silla2.setLicor(licor);
                    silla = silla2;
                }
                ReservaVuelo reservaVuelo = new ReservaVuelo(reserva, vuelo, silla, checkIn);
                reservaVuelo.getSilla().setDisponible(disponible);
                reservasVuelos.add(reservaVuelo);
                sc.nextLine();
            }
        }

    }
}
