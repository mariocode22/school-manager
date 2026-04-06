package modelo;

import java.util.Objects;

public class Modalidad extends Informacion {

    private String nombre;

    public Modalidad() {
    }

    public Modalidad(String nombre, long id) {
        super(id);
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
    }

    @Override
    public String getDescripcion() {
        return "Modalidad: " + nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}