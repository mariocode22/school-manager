package controlador;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import modelo.Estudiante;
import modelo.Persona;
import modelo.Profesor;

public class ControladorPersona {

    private final List<Persona> personas = new ArrayList<>();

    // ✅ CREATE
    public boolean createPersona(Persona persona) {

        if (verificarCedula(persona.getCedula())) {
            return false;
        }

        if (persona.getCedula() <= 0 || persona.getFechaNacimiento() == null) {
            return false;
        }

        if (persona instanceof Estudiante est && est.getCelular() <= 0) {
            return false;
        }

        return personas.add(persona);
    }

    // ✅ READ
    public Persona readPersona(long ced) {
        return personas.stream()
                .filter(p -> p.getCedula() == ced)
                .findFirst()
                .orElse(null);
    }

    // ✅ UPDATE
    public boolean updatePersona(Persona nueva) {

        for (Persona p : personas) {

            if (p.getCedula() == nueva.getCedula()) {

                p.setNombre(nueva.getNombre());
                p.setEmail(nueva.getEmail());
                p.setFechaNacimiento(nueva.getFechaNacimiento());

                if (p instanceof Profesor prof && nueva instanceof Profesor nuevoProf) {
                    prof.setApellidos(nuevoProf.getApellidos());
                }

                if (p instanceof Estudiante est && nueva instanceof Estudiante nuevoEst) {
                    est.setCelular(nuevoEst.getCelular());
                }

                return true;
            }
        }

        return false;
    }

    // ✅ DELETE
    public boolean deletePersona(long ced) {
        return personas.removeIf(p -> p.getCedula() == ced);
    }

    // ✅ VALIDACIÓN
    public boolean verificarCedula(long ced) {
        return personas.stream().anyMatch(p -> p.getCedula() == ced);
    }

    // ✅ LISTAR
    public List<Persona> getPersonas() {
        return personas;
    }

    // ✅ EDAD
    public int calcularEdad(LocalDate fecha) {
        return Period.between(fecha, LocalDate.now()).getYears();
    }
}