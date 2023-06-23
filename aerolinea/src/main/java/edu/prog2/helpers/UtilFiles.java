package edu.prog2.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.io.StringReader;
import java.util.Properties;
import java.lang.reflect.*;
import org.json.Property;
import org.json.JSONObject;

import org.json.JSONArray;

import edu.prog2.model.IFormatCSV;

public class UtilFiles {

    public static final String FILE_PATH = "./data/"; // el punto indica que se trata de la carpeta raiz

    /**
     * Devuelve true si el archivo existe y false si no.
     * 
     * @param fileName nombre del archivo
     * @return un boolean
     */
    public static boolean fileExists(String fileName) {
        Path dirPath = Paths.get(fileName);
        return Files.exists(dirPath) && !Files.isDirectory(dirPath); // sino es directorio por ende es una carpeta
    }

    public static boolean pathExists(String path) {
        Path folder = Paths.get(path);
        return Files.exists(folder) && Files.isDirectory(folder);
    }

    public static void createFolderIfNotExist(String folder) throws IOException {
        if (!pathExists(folder)) {
            Path dirPath = Paths.get(folder);
            Files.createDirectories(dirPath);
        }
    }

    /**
     * Devuelve la ruta padre de una ruta
     * 
     * @param path ruta
     * 
     * @return La ruta padre
     */
    public static String getPath(String path) {
        Path parentPath = Paths.get(path).getParent(); // el objeto puede ser nulo si no hay una ruta padre
        return parentPath == null ? null : parentPath.toString();
    }

    public static JSONObject paramsToJson(String s) throws IOException {
        s = s.replace("&", "\n");
        StringReader reader = new StringReader(s);
        Properties properties = new Properties();
        properties.load(reader);
        return Property.toJSONObject(properties);
    }

    public static Path initPath(String filePath) throws IOException {
        String path = getPath(filePath);
        createFolderIfNotExist(path);
        return Paths.get(filePath);
    }

    public static void writeText(List<?> list, String fileName) throws Exception {
        initPath(fileName);

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File(fileName), StandardCharsets.UTF_8))) {

            for (Object o : list) {
                writer.append(o.toString());
                writer.newLine();
            }

        }
    }

    public static String readText(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    public static void writeCSV(List<?> list, String fileName) throws IOException {
        UtilFiles.initPath(fileName);
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(new File(fileName), StandardCharsets.UTF_8))) { // es necesario
            for (Object obj : list) {
                IFormatCSV aux = (IFormatCSV) obj; // <-- conversión de tipo
                // este casting ayuda a acceder al metodo toCSV heredado de la interfaz
                writer.append(aux.toCSV());
            }
        }
    }

    public static void writeText(String content, String fileName) throws IOException {
        Path path = initPath(fileName);

        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }

    public static void writeJSON(List<?> list, String fileName) throws IOException {
        JSONArray jsonArray = new JSONArray(list);
        writeText(jsonArray.toString(2), fileName);
    }

    public static void writeData(List<?> list, String fileName) throws IOException {
        writeCSV(list, fileName + ".csv");
        writeJSON(list, fileName + ".json");
    }

    public static boolean exists(String fileName, String key, Object search) throws Exception {
        // obtener el archivo de reservas
        String data = readText(FILE_PATH + fileName + ".json");
        JSONArray jsonArr = new JSONArray(data);
        Constructor<?> constructor = search.getClass().getConstructor(JSONObject.class);
        // recorrerlo
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            Object current = null;

            current = constructor.newInstance(jsonObj.getJSONObject(key));

            if (current.equals(search)) {
                return true;
            }
        }
        return false;
    }
}