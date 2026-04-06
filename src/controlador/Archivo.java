package controlador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Archivo {

    // ✅ GUARDAR
    public <T> void guardar(List<T> estructura, String nombreArchivo) {

        File archivo = new File(nombreArchivo);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(estructura);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    // ✅ LEER
    @SuppressWarnings("unchecked")
    public <T> List<T> leer(String nombreArchivo) {

        File archivo = new File(nombreArchivo);

        // 🔥 Si no existe, devuelve lista vacía (CLAVE)
        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}