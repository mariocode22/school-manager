package modelo;

import java.time.LocalDate;

public class Estudiante extends Persona {

    private static final long serialVersionUID = 1L;

    private long celular;

    public Estudiante() {
    }

    public Estudiante(long cedula, String nombre, long celular, String email, LocalDate fechaNacimiento) {
        super(cedula, nombre, email, fechaNacimiento);
        setCelular(celular); // usa validación
    }

    public long getCelular() {
        return celular;
    }

    public void setCelular(long celular) {
        if (celular <= 0) {
            throw new IllegalArgumentException("Celular inválido");
        }
        this.celular = celular;
    }

    @Override
    public String tipoPersona() {
        return "Estudiante";
    }

    @Override
    public String toString() {
        return """
               ESTUDIANTE
               -------------------------
               %s
               Celular: %d
               """.formatted(super.toString(), celular);
    }
}