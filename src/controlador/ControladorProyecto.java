package controlador;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Estudiante;
import modelo.Profesor;
import modelo.Proyecto;

public class ControladorProyecto {

    private final List<Proyecto> proyectos = new ArrayList<>();
    private final ControladorPersona controladorPersona;

    public ControladorProyecto(ControladorPersona controladorPersona) {
        this.controladorPersona = controladorPersona;
    }

    // ✅ CREATE
    public boolean createProyecto(Proyecto proyecto) {

        if (verificarId(proyecto.getId())) {
            JOptionPane.showMessageDialog(null, "ID YA EXISTENTE O INVÁLIDA", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (proyecto.getId() <= 0) {
            JOptionPane.showMessageDialog(null, "ID INVÁLIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return proyectos.add(proyecto);
    }

    // ✅ READ
    public Proyecto readProyecto(long id) {
        return proyectos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ✅ UPDATE
    public boolean updateProyecto(Proyecto nuevo) {

        for (Proyecto p : proyectos) {
            if (p.getId() == nuevo.getId()) {

                p.setNombreProyecto(nuevo.getNombreProyecto());
                p.setObjetivoGeneral(nuevo.getObjetivoGeneral()); // 🔥 corregido
                p.setObjetivoEspecifico(nuevo.getObjetivoEspecifico());
                p.setLinea(nuevo.getLinea());
                p.setModalidad(nuevo.getModalidad());
                p.setNota(nuevo.getNota());

                return true;
            }
        }

        return false;
    }

    // ✅ DELETE
    public boolean deleteProyecto(long id) {
        return proyectos.removeIf(p -> p.getId() == id);
    }

    // ✅ VALIDAR ID
    private boolean verificarId(long id) {
        if (id <= 0) return true;
        return proyectos.stream().anyMatch(p -> p.getId() == id);
    }

    // ✅ LISTAR
    public List<Proyecto> enviarProyectos() {
        return proyectos;
    }

    // ✅ VINCULAR ESTUDIANTE
    public boolean vincularEstudiante(long idProyecto, long cedula) {

        Proyecto proyecto = readProyecto(idProyecto);
        if (proyecto == null) return false;

        var persona = controladorPersona.readPersona(cedula);

        if (persona instanceof Estudiante est) {
            proyecto.getEstudiantes().add(est);
            return true;
        }

        return false;
    }

    // ✅ VINCULAR PROFESOR
    public boolean vincularProfesor(long idProyecto, long cedula) {

        Proyecto proyecto = readProyecto(idProyecto);
        if (proyecto == null) return false;

        var persona = controladorPersona.readPersona(cedula);

        if (persona instanceof Profesor prof) {
            proyecto.getProfesores().add(prof);
            return true;
        }

        return false;
    }

    // ✅ DESVINCULAR ESTUDIANTE
    public boolean desvincularEstudiante(long idProyecto, long cedula) {

        Proyecto proyecto = readProyecto(idProyecto);
        if (proyecto == null) return false;

        return proyecto.getEstudiantes()
                .removeIf(e -> e.getCedula() == cedula);
    }

    // ✅ DESVINCULAR PROFESOR
    public boolean desvincularProfesor(long idProyecto, long cedula) {

        Proyecto proyecto = readProyecto(idProyecto);
        if (proyecto == null) return false;

        return proyecto.getProfesores()
                .removeIf(p -> p.getCedula() == cedula);
    }

    // ✅ REPORTE NOTA ≤ 3
    public String reporteN3() {

        StringBuilder reporte = new StringBuilder();

        for (Proyecto p : proyectos) {
            if (p.getNota() <= 3) {
                reporte.append("Proyecto: ")
                        .append(p.getNombreProyecto())
                        .append(" Nota: ")
                        .append(p.getNota())
                        .append("\n");
            }
        }

        return reporte.toString();
    }

    // ✅ REPORTE GENERAL
    public String reportes() {

        StringBuilder reporte = new StringBuilder();

        for (Proyecto p : proyectos) {
            reporte.append("Proyecto: ")
                    .append(p.getNombreProyecto())
                    .append("\nProfesores:\n")
                    .append(p.mostrarProfesores())
                    .append("Estudiantes:\n")
                    .append(p.mostrarEstudiantes())
                    .append("\n\n");
        }

        return reporte.toString();
    }

    // ✅ GENERAR ID
    public int generarId() {
        return proyectos.size() + 1;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (Proyecto p : proyectos) {
            sb.append("ID: ")
                    .append(p.getId())
                    .append(" - ")
                    .append(p.getNombreProyecto())
                    .append("\n");
        }

        return sb.toString();
    }
}