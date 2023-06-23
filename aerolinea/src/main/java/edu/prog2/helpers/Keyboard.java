package edu.prog2.helpers;

import java.util.InputMismatchException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Keyboard {
    public static Scanner sc = new Scanner(System.in).useDelimiter("[\n]+|[\r\n]+");

    /**
     * devuelve un valor valido de tipo entero despues de haber pasado por el try y
     * catch
     * 
     * @param mensaje mensaje que se muestra al usuario
     * @return un valor entero
     */
    public static int readInt(String mensaje) {
        boolean ok;
        int value = Integer.MIN_VALUE; // min_value almacena el valor minimo posible para cualquier variable
        System.out.print(mensaje);

        do {
            try { // intente ejecutar las instrucciones del bloque
                ok = true;
                value = sc.nextInt();
            } catch (InputMismatchException e) { // capture posibles excepciones
                ok = false;
                System.out.print(">> Valor erróneo. " + mensaje);
            } finally { // suceda lo que suceda, ejecute las instrucciones del bloque
                sc.nextLine();
            }
        } while (!ok);

        return value;
    }

    public static int readInt(int from, int to, String mensaje) {
        int value;
        int tmp = Math.min(from, to);
        if (tmp == to) {
            to = from;
            from = tmp;
        }

        do {
            value = readInt(mensaje);
            if (value < from || value > to) {
                System.out.print("Rango inválido. ");
            }
        } while (value < from || value > to);

        return value;
    }

    /**
     * imprime un mensaje y devuelve una entrada de string
     * 
     * @param mensaje mensaje que se muestra al usuario
     * @return devuelve una entrada de string
     */
    public static String readString(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    public static String readString(int from, int to, String message) {
        String value;
        int tmp = Math.min(from, to);
        if (tmp == to) {
            to = from;
            from = tmp;
        }

        int length;

        do {
            value = readString(message);
            length = value.length();
            if (length < from || length > to) {
                System.out.print("Longitud no permitida. ");
            }
        } while (length < from || length > to);

        return value;
    }

    /**
     * Devuelve un valor valido de tipo Long despues de haber pasado por el try y
     * catch
     * 
     * @param mensaje mensaje que se muestra al usuario
     * @return un valor de tipo Long
     */
    public static long readLong(String mensaje) {
        boolean ok;
        Long value = Long.MIN_VALUE; // min_value almacena el valor minimo posible para cualquier variable
        System.out.print(mensaje);

        do {
            try { // intente ejecutar las instrucciones del bloque
                ok = true;
                value = sc.nextLong();
            } catch (InputMismatchException e) { // capture posibles excepciones
                ok = false;
                System.out.print(">> Valor erróneo. " + mensaje);
            } finally { // suceda lo que suceda, ejecute las instrucciones del bloque
                sc.nextLine();
            }
        } while (!ok);

        return value;
    }

    public static long readLong(long from, long to, String mensaje) {
        long value;
        long tmp = Math.min(from, to);
        if (tmp == to) {
            to = from;
            from = tmp;
        }

        do {
            value = readLong(mensaje);
            if (value < from || value > to) {
                System.out.print("Rango inválido. ");
            }
        } while (value < from || value > to);

        return value;
    }

    /**
     * devuelve un valor valido de tipo Double despues de haber pasado por el try y
     * catch
     * 
     * @param mensaje mensaje que se muestra al usuario
     * @return un valor Double
     */
    public static double readDouble(String mensaje) {
        boolean ok;
        Double value = Double.NaN; // min_value almacena el valor minimo posible para cualquier variable
        System.out.print(mensaje);

        do {
            try { // intente ejecutar las instrucciones del bloque
                ok = true;
                value = sc.nextDouble();
            } catch (InputMismatchException e) { // capture posibles excepciones
                ok = false;
                System.out.print(">> Valor erróneo. " + mensaje);
            } finally { // suceda lo que suceda, ejecute las instrucciones del bloque
                sc.nextLine();
            }
        } while (!ok);

        return value;

    }

    public static Double readDouble(Double from, Double to, String mensaje) {
        Double value;
        Double tmp = Math.min(from, to);
        if (tmp == to) {
            to = from;
            from = tmp;
        }

        do {
            value = readDouble(mensaje);
            if (value < from || value > to) {
                System.out.print("Rango inválido. ");
            }
        } while (value < from || value > to);

        return value;
    }

    /**
     * devuelve un valor valido de tipo booleano despues de haber pasado por el try
     * y catch
     * 
     * @param message mensaje que se muestra al usuario
     * @return un valor de tipo booleano
     */
    public static boolean readBoolean(String message) {
        boolean ok;
        boolean value = false;
        System.out.print(message);

        do {
            try {
                ok = true;
                String str = ' ' + sc.next().toLowerCase().trim() + ' ';
                if (" si s true t yes y ".contains(str)) { // evalua si alguna de esas opciones esta en el string
                    value = true;
                } else if (" no n false f not ".contains(str)) {
                    value = false;
                } else {
                    throw new InputMismatchException(); // si no lanza una excepcion
                }
            } catch (InputMismatchException e) {
                ok = false;
                System.out.print(">> Se esperaba [si|s|true|t|yes|y|no|not|n|false|f]"
                        + message);
            } finally {
                sc.nextLine();
            }
        } while (!ok);

        return value;
    }

    /**
     * devuelve un valor valido de tipo Local Date (Fecha) despues de haber pasado
     * por el try y catch
     * 
     * @param message mensaje que se muestra al usuario
     * @return una fecha
     */
    public static LocalDate readDate(String message) {
        boolean ok;
        LocalDate date = LocalDate.now();
        System.out.print(message);

        do {
            try {
                ok = true;
                String strDate = sc.next().trim();
                if (!strDate.toLowerCase().equals("hoy")) {
                    date = LocalDate.parse(strDate);
                }
            } catch (DateTimeParseException dtps) {
                ok = false;
                System.out.print(">> Fecha errónea. " + message);
            } finally {
                sc.nextLine();
            }
        } while (!ok);

        return date;
    }

    /**
     * devuelve un valor valido de tipo Local Date Time (Fecha y hora) despues de
     * haber pasado por el try y catch
     * 
     * @param message mensaje que se muestra al usuario
     * @return una Fecha y una hora
     */
    public static LocalDateTime readDateTime(String message) {
        // No se me es posible quitar el Locale.English ya que es de la unica forma que
        // funciona en mi computador
        // Ejemplo del formato que funciona: 2005-01-02 03:15 PM
        boolean ok;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.print(message + "(Ejemplo: 2005-01-02 03:15 PM)>>");

        do {
            try {
                ok = true;
                String strDate = sc.next().trim();
                if (!strDate.toLowerCase().equals("ahora")) {
                    dateTime = LocalDateTime.parse(strDate, formatter);
                }
            } catch (DateTimeParseException dtps) {
                ok = false;
                dtps.printStackTrace();
                System.out.print(">> Fecha y hora erróneas. " + message);
            } finally {
                sc.nextLine();
            }
        } while (!ok);

        return dateTime;
    }

    /**
     * devuelve el estado del enum
     * 
     * @param message mensaje que se muestra al usuario
     * @param c       nombre de la clase
     * @return el estado del enum
     */
    public static <T extends Enum<T>> T readEnum(Class<T> c, String message) {
        boolean ok;
        System.out.print(message);

        if (c != null) {
            do {
                ok = true;
                try { // pequeño cambio para que el usuario no tenga que usar guion bajo
                    String type = sc.nextLine().trim().toUpperCase();
                    return type.length() > 0 ? Enum.valueOf(c, type) : null;
                } catch (IllegalArgumentException ex) {
                    ok = false;
                    System.out.printf(
                            ">> %s no incluye el valor esperado. %s",
                            c.getSimpleName(), message);
                }
            } while (!ok);
        }

        return null;
    }

    public static Duration readDuration(String message) {
        boolean ok;
        Duration dateTime = null;
        System.out.print(message + "(HH:MM)>>");

        do {
            try {
                ok = true;
                String strDate = sc.next().trim();
                String[] a = strDate.split(":");
                dateTime = Duration.parse("PT" + a[0] + "H" + a[1] + "M");

            } catch (Exception dtps) {
                ok = false;
                dtps.printStackTrace();
                System.out.print(">> Duracion errónea. " + message);
            } finally {
                sc.nextLine();
            }
        } while (!ok);

        return dateTime;
    }

    public static <T extends Enum<T>> T readEnum(Class<T> c) {
        Object[] allItems = (EnumSet.allOf(c)).toArray();
        String message = String.format("%nOpciones de %s:", c.getSimpleName());

        int i;
        for (i = 0; i < allItems.length; i++) {
            String item = allItems[i].toString().replaceAll("_", " ");
            message += String.format("%n%3d-%s", i + 1, item);
        }

        message = String.format(
                "%s%nElija un tipo entre 1 y %d: ", message, allItems.length);

        do {
            i = readInt(message);
        } while (i < 1 || i > allItems.length);

        return Enum.valueOf(c, allItems[i - 1].toString());
    }
}
