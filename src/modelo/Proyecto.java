package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Proyecto {

    private long id;
    private String nombreProyecto;
    private String objetivoGeneral;
    private String objetivoEspecifico;
    private Modalidad modalidad;
    private LineaInvestigacion linea;
    private List<Profesor> profesores;
    private List<Estudiante> estudiantes;
    private double nota;

    public Proyecto() {
        this.profesores = new ArrayList<>();
        this.estudiantes = new ArrayList<>();
    }

    public Proyecto(long id, String nombreProyecto, String objetivoGeneral, String objetivoEspecifico,
                    Modalidad modalidad, LineaInvestigacion linea,
                    List<Profesor> profesores, List<Estudiante> estudiantes, double nota) {

        setId(id);
        setNombreProyecto(nombreProyecto);
        setObjetivoGeneral(objetivoGeneral);
        setObjetivoEspecifico(objetivoEspecifico);
        this.modalidad = modalidad;
        this.linea = linea;
        this.profesores = (profesores != null) ? profesores : new ArrayList<>();
        this.estudiantes = (estudiantes != null) ? estudiantes : new ArrayList<>();
        setNota(nota);
    }

    // ✅ GETTERS Y SETTERS

    public long getId() { return id; }

    public void setId(long id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        this.id = id;
    }

    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = Objects.requireNonNull(nombreProyecto, "Nombre obligatorio");
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = Objects.requireNonNull(objetivoGeneral, "Objetivo general obligatorio");
    }

    public String getObjetivoEspecifico() {
        return objetivoEspecifico;
    }

    public void setObjetivoEspecifico(String objetivoEspecifico) {
        this.objetivoEspecifico = Objects.requireNonNull(objetivoEspecifico, "Objetivo específico obligatorio");
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public LineaInvestigacion getLinea() {
        return linea;
    }

    public void setLinea(LineaInvestigacion linea) {
        this.linea = linea;
    }

    public double getNota() { return nota; }

    public void setNota(double nota) {
        if (nota < 0 || nota > 5) {
            throw new IllegalArgumentException("Nota debe estar entre 0 y 5");
        }
        this.nota = nota;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    // 🔥 MÉTODOS MEJORADOS

    public boolean añadirProfesor(Profesor nuevo) {
        if (nuevo == null) return false;

        if (profesores.stream().noneMatch(p -> p.getCedula() == nuevo.getCedula())) {
            profesores.add(nuevo);
            return true;
        }
        return false;
    }

    public boolean añadirEstudiante(Estudiante nuevo) {
        if (nuevo == null) return false;

        if (estudiantes.stream().noneMatch(e -> e.getCedula() == nuevo.getCedula())) {
            estudiantes.add(nuevo);
            return true;
        }
        return false;
    }

    public boolean removerProfesor(long cedula) {
        return profesores.removeIf(p -> p.getCedula() == cedula);
    }

    public boolean removerEstudiante(long cedula) {
        return estudiantes.removeIf(e -> e.getCedula() == cedula);
    }

    // ✅ Mostrar información

    public String mostrarProfesores() {
        StringBuilder sb = new StringBuilder();

        for (Profesor p : profesores) {
            sb.append("CC. ")
                    .append(p.getCedula())
                    .append(" ")
                    .append(p.getNombre())
                    .append(" ")
                    .append(p.getApellidos())
                    .append("\n");
        }

        return sb.toString();
    }

    public String mostrarEstudiantes() {
        StringBuilder sb = new StringBuilder();

        for (Estudiante e : estudiantes) {
            sb.append("CC. ")
                    .append(e.getCedula())
                    .append(" ")
                    .append(e.getNombre())
                    .append("\n");
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return """
               PROYECTO
               -------------------------
               ID: %d
               Nombre: %s
               Modalidad: %s
               Línea: %s
               Nota: %.2f
               """.formatted(
                id,
                nombreProyecto,
                modalidad != null ? modalidad.getNombre() : "N/A",
                linea != null ? linea.getNombreInv() : "N/A",
                nota
        );
    }
}