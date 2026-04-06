package modelo;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Profesor extends Persona {


    private String apellidos;

    public Profesor() {
    }

    public Profesor(long cedula, String nombre, String apellidos, String email, LocalDate fechaNacimiento) {
        super(cedula, nombre, email, fechaNacimiento);
        this.apellidos = Objects.requireNonNull(apellidos, "Apellidos no pueden ser null");
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = Objects.requireNonNull(apellidos, "Apellidos no pueden ser null");
    }

    @Override
    public String tipoPersona() {
        return "Profesor";
    }

    // Método útil
    public int getEdad() {
        return Period.between(getFechaNacimiento(), LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return """
               PROFESOR
               -------------------------
               Nombre: %s %s
               Email: %s
               Edad: %d
               """.formatted(
                getNombre(),
                apellidos,
                getEmail(),
                getEdad()
        );
    }
}