package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    private long cedula;
    private String nombre;
    private String email;
    private LocalDate fechaNacimiento;

    public Persona() {
    }

    public Persona(long cedula, String nombre, String email, LocalDate fechaNacimiento) {
        this.cedula = cedula;
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
        this.email = Objects.requireNonNull(email, "El email no puede ser null");
        this.fechaNacimiento = Objects.requireNonNull(fechaNacimiento, "La fecha no puede ser null");
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        if (cedula <= 0) {
            throw new IllegalArgumentException("La cédula debe ser mayor a 0");
        }
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser null");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Fecha inválida");
        }
        this.fechaNacimiento = fechaNacimiento;
    }

    // Método útil para depuración
    @Override
    public String toString() {
        return """
               Persona:
               Cédula: %d
               Nombre: %s
               Email: %s
               Fecha de nacimiento: %s
               """.formatted(cedula, nombre, email, fechaNacimiento);
    }

    // Buenas prácticas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona persona)) return false;
        return cedula == persona.cedula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    // 💡 Aprovechando que es abstracta
    public abstract String tipoPersona();
}