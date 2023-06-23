package edu.prog2;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import edu.prog2.helpers.Keyboard;
import edu.prog2.model.*;
import edu.prog2.services.*;

public class App {

    static PasajerosService pasajeros;
    static TrayectosService trayectos;
    static AvionesService aviones;
    static SillasService sillas;
    static VuelosService vuelos;
    static ReservasService reservas;
    static ReservasVuelosService reservasVuelos;

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {
        try {
            inicializar();
            ArrayList<IFormatCSV> list = new ArrayList<>();
            list.add(aviones.get(0));
            list.add(pasajeros.get(0));

            for (IFormatCSV item : list) {
                System.out.println(item.toCSV());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        do {
            try {
                int opcion = leerOpcion();

                switch (opcion) {
                    case 1:
                        listarPasajeros();
                        reservas.getList()
                                .add(new Reserva(LocalDateTime.parse("2022-09-10T08:30"), pasajeros.getList().get(1)));
                        System.out.println(reservas.getList().get(0));
                        break;
                    case 2:
                        listarTrayectos();
                        break;
                    case 3:
                        listarAviones();
                        break;
                    case 4:
                        listarSillas(aviones.get(0));
                        break;
                    case 5:
                        listarVuelos();
                        break;
                    case 6:
                        listarSillasDisponiblesEnVuelo(vuelos.get(0));
                        break;
                    case 7:
                        elegirSillaDisponible(vuelos.get(0));
                        break;
                    case 8:
                        elegirPasajero();
                        break;
                    case 9:
                        elegirVuelo();
                        break;
                    case 10:
                        crearReservas();
                        break;
                    case 11:
                        listarReservas();
                        break;
                    case 12:
                        listarAvionesCSV();
                        break;
                    case 13:
                        listarPasajerosCSV();
                        break;
                    case 14:
                        listarTrayectosCSV();
                        break;
                    case 15:
                        listarVuelosCSV();
                        break;
                    case 16:
                        listarSillasCSV();
                        break;
                    case 17:
                        listarReservasCSV();
                        break;
                    case 18:
                        listarReservasVuelosCSV();
                        break;
                    case 19:
                        crearPasajeros();
                        break;
                    case 20:
                        crearTrayectos();
                        break;
                    case 21:
                        crearAviones();
                        break;
                    case 22:
                        programarVuelos();
                        break;
                    case 23:
                        ListarSillasConAviones();
                        break;
                    case 24:
                        vuelos.listAll();
                        break;
                    case 25:
                        pasajeros.listAll();
                        break;
                    case 26:
                        aviones.listAll(sillas);
                        break;
                    case 27:
                        trayectos.listAll();
                        break;
                    case 28:
                        reservas.listAll(reservasVuelos);
                        break;
                    case 99:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    static int leerOpcion() {
        String opciones = "\nMenú de opciones:\n"
                + " 1 - Listar pasajeros                      15 - Listar Vuelos CSV                \n"
                + " 2 - Listar trayectos                      16 - Listar Sillas CSV                \n"
                + " 3 - Listar aviones                        17 - Listar Reservas CSV              \n"
                + " 4 - Listar sillas                         18 - listar Vuelos de las Reservas CSV\n"
                + " 5 - Listar vuelos                         19 - Crear Pasajeros                  \n"
                + " 6 - Listar sillas disponibles             20 - Crear Trayectos                  \n"
                + " 7 - Elegir sillas de vuelo                21 - Crear Aviones                    \n"
                + " 8 - Elegir pasajero                       22 - Programar Vuelos                 \n"
                + " 9 - Elegir vuelos                         23 - Listar sillas con aviones        \n"
                + "10 - Crear reservacion                     24 - Vuelos TXT                       \n"
                + "11 - Listar reservaciones                  25 - Pasajeros TXT                    \n"
                + "12 - Listar Aviones CSV                    26 - Aviones TXT                      \n"
                + "13 - Listar pasajeros CSV                  27 - Trayectos TXT                    \n"
                + "14 - Listar Trayectos CSV                  28 - Reservas TXT                     \n"
                + "99 - Salir\n"
                + "\nElija una opción (99 para salir) > ";

        int opcion = Keyboard.readInt(opciones);
        System.out.println();
        return opcion;
    }

    private static void inicializar() throws IOException {
        pasajeros = new PasajerosService();
        trayectos = new TrayectosService();
        aviones = new AvionesService();
        sillas = new SillasService(aviones);
        vuelos = new VuelosService(trayectos, aviones);
        reservas = new ReservasService(pasajeros);
        reservasVuelos = new ReservasVuelosService(reservas, vuelos, sillas);
    }

    private static void ListarSillasConAviones() {
        for (Avion a : aviones.getList()) {
            listarSillas(a);
        }
    }

    static void listarAvionesCSV() {
        for (Avion avion : aviones.getList()) {
            System.out.print(avion.toCSV());
        }
    }

    static void listarReservasCSV() {
        for (Reserva reserva : reservas.getList()) {
            System.out.print(reserva.toCSV());
        }
    }

    static void listarSillasCSV() {
        for (Silla silla : sillas.getList()) {
            System.out.print(silla.toCSV());
        }
    }

    static void listarPasajerosCSV() {
        for (Pasajero pasajero : pasajeros.getList()) {
            System.out.print(pasajero.toCSV());
        }
    }

    static void listarTrayectosCSV() {
        for (Trayecto trayecto : trayectos.getList()) {
            System.out.print(trayecto.toCSV());
        }
    }

    static void listarVuelosCSV() {
        for (Vuelo vuelo : vuelos.getList()) {
            System.out.print(vuelo.toCSV());
        }
    }

    static void listarReservasVuelosCSV() {
        for (ReservaVuelo reservaVuelo : reservasVuelos.getList()) {
            System.out.print(reservaVuelo.toCSV());
        }
    }

    private static void crearReservas() throws IOException {
        do {
            Pasajero pasajero = elegirPasajero();
            if (pasajero == null) {
                break;
            }

            LocalDateTime fechaHora = Keyboard.readDateTime("Ingresar la fecha y la hora de la reserva: ");
            System.out.println();
            Reserva reserva = new Reserva(fechaHora, pasajero);
            boolean ok = false;

            do {
                Vuelo vuelo = elegirVuelo();
                System.out.println();
                if (vuelo == null) {
                    break;
                }
                // Ver asigancion de menu y licor en elegirSillaDisponible
                Silla silla = elegirSillaDisponible(vuelo);
                ReservaVuelo reservaVuelo = new ReservaVuelo(reserva, vuelo, silla);
                reservaVuelo.getSilla().setDisponible(false);
                reservasVuelos.add(reservaVuelo);

                ok = true;
                System.out.println("\nReserva registrada");
            } while (true);

            if (ok) {
                reservas.add(reserva);
            }
        } while (true);

    }

    private static void listarVuelosReserva(Reserva r) {
        System.out.println("\n\n Vuelos: ");
        for (ReservaVuelo rv : reservasVuelos.getList()) {
            if (r.equals(rv.getReserva())) {
                System.out.println(rv);
            }
        }
    }

    private static void listarReservas() {
        for (Reserva r : reservas.getList()) {
            String fecha2 = String.valueOf(r.getFechaHora()).replaceAll("T", " ");
            String fecha = fecha2.substring(0, 16);
            System.out.printf("Fecha y hora de la reserva: %s - Estado: %s \n Pasajero: %s - %s %s", fecha,
                    r.getCanceladaString(), r.getPasajero().getIdentifiacion(),
                    r.getPasajero().getNombre(),
                    r.getPasajero().getApellido());
            listarVuelosReserva(r);
        }
    }

    private static Vuelo elegirVuelo() {
        System.out.println("Vuelos programados:");
        for (int i = 0; i < vuelos.getList().size(); i++) {
            Vuelo p = vuelos.get(i);
            String fecha = String.valueOf(p.strFechaHora()).replaceAll("T", " ");
            System.out.printf("%2d: %s %s %s - %s%n",
                    (i + 1), fecha, p.getAvion().getMatricula(), p.getTrayecto().getOrigen(),
                    p.getTrayecto().getDestino());
        }

        int i;
        do {
            i = Keyboard.readInt("Elija el índice del pasajero (0-Termina): ");
            if (i == 0) {
                break;
            }
            if (i < 0 || i > vuelos.getList().size()) {
                System.out.printf("Error. Elija un índice entre 0 y %d%n",
                        vuelos.getList().size());
            }
        } while (i < 0 || i > vuelos.getList().size());

        return i > 0 ? vuelos.get(i - 1) : null;
    }

    static Avion elegirAvion() {
        System.out.println("Aviones disponibles:");
        for (int i = 0; i < aviones.getList().size(); i++) {
            Avion p = aviones.get(i);
            System.out.printf("%2d: %s-%s%n",
                    (i + 1), p.getMatricula(), p.getModelo());
        }

        int i;
        do {
            i = Keyboard.readInt("Elija el índice del avion (0-Termina): ");
            if (i < 0 || i > aviones.getList().size()) {
                System.out.printf("Error. Elija un índice entre 0 y %d%n",
                        aviones.getList().size());
            }
        } while (i < 0 || i > aviones.getList().size());

        return i > 0 ? aviones.get(i - 1) : null;
    }

    static Trayecto elegirTrayecto() {
        System.out.println("Trayectos disponibles:");
        for (int i = 0; i < trayectos.getList().size(); i++) {
            Trayecto p = trayectos.get(i);
            System.out.printf("%2d: %-20s %-20s %-20.0f %-10s%n",
                    (i + 1), p.getOrigen(), p.getDestino(), p.getCosto(), p.getDuracionS());
        }

        int i;
        do {
            i = Keyboard.readInt("Elija el índice del avion (0–Termina): ");
            if (i < 0 || i > trayectos.getList().size()) {
                System.out.printf("Error. Elija un índice entre 0 y %d%n",
                        trayectos.getList().size());
            }
        } while (i < 0 || i > trayectos.getList().size());

        return i > 0 ? trayectos.get(i - 1) : null;
    }

    static Pasajero elegirPasajero() {
        System.out.println("Pasajeros:");
        for (int i = 0; i < pasajeros.getList().size(); i++) {
            Pasajero p = pasajeros.get(i);
            System.out.printf("%2d: %s-%s %s%n",
                    (i + 1), p.getIdentifiacion(), p.getNombre(), p.getApellido());
        }

        int i;
        do {
            i = Keyboard.readInt("Elija el índice del pasajero (0-Termina): ");
            System.out.println("");
            if (i < 0 || i > pasajeros.getList().size()) {
                System.out.printf("Error. Elija un índice entre 0 y %d%n",
                        pasajeros.getList().size());
            }
            if (i == 0) {
                break;
            }
        } while (i < 0 || i > pasajeros.getList().size());

        return i > 0 ? pasajeros.get(i - 1) : null;
    }

    private static ArrayList<Silla> sillasReservadasEnVuelo(Vuelo vuelo) {
        ArrayList<Silla> sillasAvion = new ArrayList<>();

        for (ReservaVuelo rv : reservasVuelos.getList()) {
            if (!rv.getReserva().getCancelada())
                if (rv.getVuelo().equals(vuelo)) {
                    if (rv.getSilla().getAvion().equals(vuelo.getAvion())) {
                        Silla silla = rv.getSilla();
                        sillasAvion.add(silla);
                    }
                }
        }
        return sillasAvion;
    }

    private static void listarVuelos() {
        if (vuelos == null) {
            System.out.println("Use primero la opción crear array de sillas");
        } else {
            for (Vuelo a : vuelos.getList()) {
                System.out.println(a);
            }
        }
    }

    private static boolean sillaDisponibleEnVuelo(Silla silla, Vuelo vuelo) {
        ArrayList<Silla> sillasR = sillasReservadasEnVuelo(vuelo);
        for (Silla s : sillasR) {
            if (s.equals(silla)) {
                return false;
            }
        }
        return true;
    }

    private static Silla elegirSillaDisponible(Vuelo vuelo) throws IOException {
        int indice;
        Silla sillaEscogida;
        listarSillasDisponiblesEnVuelo(vuelo);
        while (true) {
            indice = Keyboard.readInt("\n Elija el indice de una de las sillas: ");
            System.out.println(" ");
            try {
                ArrayList<Silla> sillasDisponiblesVuelo = sillasDisponiblesEnVuelo(vuelo);
                sillaEscogida = sillasDisponiblesVuelo.get(indice);
                if (sillaEscogida instanceof SillaEjecutiva) {
                    Menu menu = Keyboard.readEnum(Menu.class);
                    SillaEjecutiva aux = (SillaEjecutiva) sillaEscogida;
                    aux.setMenu(menu);
                    Licor licor = Keyboard.readEnum(Licor.class);
                    aux.setLicor(licor);
                    sillaEscogida = aux;
                }
                return sillaEscogida;
            } catch (Exception e) {
                System.out.println("La silla que escogio no existe");
            }
        }
    }

    public static ArrayList<Silla> sillasDisponiblesEnVuelo(Vuelo vuelo) throws IOException { /////////////////////////////////////////
        System.out.println("Adentro");
        inicializar();
        ArrayList<Silla> sillasDisponibles = new ArrayList<>();

        // vuelo = vuelos.get(vuelo);
        for (Silla s : sillas.getList()) {
            if (sillaDisponibleEnVuelo(s, vuelo)) {
                if (s.getAvion().equals(vuelo.getAvion())) {
                    sillasDisponibles.add(s);
                }
            }
        }
        return sillasDisponibles;

    }

    private static void listarSillasDisponiblesEnVuelo(Vuelo vuelo) throws IOException {
        Trayecto trayecto = vuelo.getTrayecto();
        String fecha = String.valueOf(vuelo.strFechaHora()).replaceAll("T", " ");
        System.out.printf("Listado de sillas disponibles en el vuelo %s: %s - %s - %s \n",
                vuelo.getAvion().getMatricula(),
                trayecto.getOrigen(), trayecto.getDestino(), fecha);

        ArrayList<Silla> sillasDisponibles = sillasDisponiblesEnVuelo(vuelo);
        int i = 0;

        for (Silla e : sillasDisponibles) {
            String tipoSilla = (e instanceof SillaEjecutiva) ? "E" : "S";

            System.out.printf("%s-%s-%s   ",
                    (sillasDisponibles.indexOf(e) < 10 && sillasDisponibles.indexOf(e) > 0)
                            ? " " + sillasDisponibles.indexOf(e)
                            : "" + sillasDisponibles.indexOf(e),
                    e.getPosicion(),
                    tipoSilla);
            i++;
            if (i % 10 == 0) {
                System.out.println();
            }

        }
    }

    private static void programarVuelos() throws IOException {
        System.out.println("Programación de vuelos\n");

        do {
            Trayecto trayecto = elegirTrayecto();

            if (trayecto == null) {
                return;
            }

            LocalDateTime fechaHora = Keyboard.readDateTime("Fecha y hora: ");

            Avion avion = elegirAvion();
            if (avion == null) {
                return;
            }

            boolean ok = vuelos.add(new Vuelo(fechaHora, trayecto, avion));

            if (ok) {
                System.out.printf(
                        "Se agregó %s-%s, %s, fecha/hora: %s (%10.0f) %n", trayecto.getOrigen(), trayecto.getDestino(),
                        avion.getMatricula(), fechaHora, trayecto.getCosto());
            }

        } while (true);
    }

    private static void listarSillas(Avion avion) {
        if (avion == null || sillas == null) {
            System.out.println("Problemas con los ArrayList de aviones y de sillas");
        } else {
            System.out.println("SILLAS DEL AVIÓN " + avion.getMatricula());
            for (Silla s : sillas.getList()) {
                if (s.getAvion().equals(avion)) {
                    System.out.printf("%-5d%3s%n", sillas.getList().indexOf(s), s);
                }
            }
        }
    }

    private static void crearAviones() throws IOException {
        System.out.println("Ingreso de Aviones\n");

        do {
            String matricula = Keyboard.readString(
                    0, 26, "Matricula (Intro termina): ");

            if (matricula.length() == 0) {
                return;
            }

            String modelo = Keyboard.readString(2, 27, "Modelo: ");
            int sillasEjecutivas = 0;
            int sillasEconomicas = 0;
            while (true) {
                sillasEjecutivas = Keyboard.readInt(4, 200, "Cuantas sillas ejecutivas: ");
                if (sillasEjecutivas % 4 == 0) {
                    break;
                } else {
                    System.out.println("¡Error! el numero no es multiplo de 4");
                }
            }
            while (true) {
                sillasEconomicas = Keyboard.readInt(6, 500, "Cuantas sillas economicas: ");
                if (sillasEconomicas % 6 == 0) {
                    break;
                } else {
                    System.out.println("¡Error! el numero no es multiplo de 6");
                }
            }
            Avion avion = new Avion(matricula, modelo);
            boolean ok = aviones.add(avion);

            if (ok) {
                System.out.printf(
                        "Se agregó el vuelo correctamente%n");

                sillas.create(avion, sillasEjecutivas, sillasEconomicas);
            }

        } while (true);
    }

    private static void listarAviones() {
        if (aviones == null || sillas == null) {
            System.out.println("Use primero la opción crear array de aviones");
        } else {
            for (Avion a : aviones.getList()) {
                System.out.println(a);
            }
        }
    }

    private static void listarTrayectos() {
        if (trayectos == null) {
            System.out.println("Use primero la opción crear array de trayectos");
        } else {
            for (Trayecto t : trayectos.getList()) {
                System.out.println(t);
            }
        }
    }

    private static void crearTrayectos() throws IOException {
        System.out.println("Ingreso de trayectos\n");
        do {

            String origen = Keyboard.readString(0, 25, "Origen(Intro termina): ");

            if (origen.length() == 0) {
                return;
            }
            String destino = Keyboard.readString(3, 34, "Destino: ");
            Duration duracion = Keyboard.readDuration("Duracion:");
            double costo = Keyboard.readDouble(10.0, 90000000.0, "Costo: ");

            boolean ok = trayectos.add(new Trayecto(origen, destino, duracion, costo));

            if (ok) {
                System.out.printf("Se agregó el Trayecto %s-%s%n", origen, destino);
            }

        } while (true);
    }

    private static void listarPasajeros() {
        if (pasajeros == null) {
            System.out.println("Use primero la opción crear array de pasajeros");
        } else {
            for (Pasajero p : pasajeros.getList()) {
                System.out.println(p);
            }
        }
    }

    private static void crearPasajeros() throws IOException {
        System.out.println("Ingreso de pasajeros\n");

        do {
            String identificacion = Keyboard.readString(
                    0, 50, "Identificación (Intro termina): ");

            if (identificacion.length() == 0) {
                return;
            }

            String nombres = Keyboard.readString(3, 25, "Nombres: ");
            String apellidos = Keyboard.readString(3, 30, "Apellidos: ");

            boolean ok = pasajeros.add(new Pasajero(
                    identificacion, nombres, apellidos));

            if (ok) {
                System.out.printf(
                        "Se agregó el pasajero con identificación: %s%n%n", identificacion);
            }

        } while (true);
    }

}