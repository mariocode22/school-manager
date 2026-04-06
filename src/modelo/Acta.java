package modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Acta implements Serializable, Comparable<Acta> {

    private static final long serialVersionUID = 1L;

    // Atributos
    private long codigoActa;
    private LocalDate fecha;
    private String observaciones;
    private long codigoProyecto;

    // Constructor vacío
    public Acta() {
    }

    // Constructor completo
    public Acta(long codigoActa, LocalDate fecha, String observaciones, long codigoProyecto) {
        this.codigoActa = codigoActa;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.codigoProyecto = codigoProyecto;
    }

    // Getters y Setters
    public long getCodigoActa() {
        return codigoActa;
    }

    public void setCodigoActa(long codigoActa) {
        this.codigoActa = codigoActa;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public long getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(long codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    // toString moderno
    @Override
    public String toString() {
        return """
               -----------------------------------------------------------------
               ACTA NUMERO:
               %d
               -----------------------------------------------------------------
               FECHA DE CREACIÓN:
               %s
               OBSERVACIONES:
               %s
               -----------------------------------------------------------------
               """.formatted(codigoActa, fecha, observaciones);
    }

    // Comparación segura
    @Override
    public int compareTo(Acta otra) {
        return Objects.compare(this.fecha, otra.fecha, LocalDate::compareTo);
    }

    // Buenas prácticas: equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Acta acta)) return false;
        return codigoActa == acta.codigoActa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoActa);
    }
}