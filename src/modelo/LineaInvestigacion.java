package modelo;

import java.util.Objects;

public class LineaInvestigacion extends Informacion {

    private static final long serialVersionUID = 1L;

    private String nombreInv;

    public LineaInvestigacion() {
    }

    public LineaInvestigacion(String nombreInv, long id) {
        super(id);
        this.nombreInv = Objects.requireNonNull(nombreInv, "El nombre no puede ser null");
    }

    public String getNombreInv() {
        return nombreInv;
    }

    public void setNombreInv(String nombreInv) {
        this.nombreInv = Objects.requireNonNull(nombreInv, "El nombre no puede ser null");
    }

    @Override
    public String getDescripcion() {
        return "Línea de investigación: " + nombreInv;
    }

    @Override
    public String toString() {
        return """
               LINEA DE INVESTIGACIÓN
               -------------------------
               ID: %d
               Nombre: %s
               """.formatted(getId(), nombreInv);
    }
}