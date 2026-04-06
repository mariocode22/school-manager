package controlador;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Informacion;
import modelo.LineaInvestigacion;
import modelo.Modalidad;

public class ControladorInformacion {

    private final List<Informacion> infos = new ArrayList<>();

    // ✅ CREATE
    public boolean crearInformacion(Informacion inf) {

        if (existeId(inf.getId())) {
            JOptionPane.showMessageDialog(null, "ID YA EXISTENTE O INVÁLIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (inf.getId() <= 0) {
            JOptionPane.showMessageDialog(null, "ID INVÁLIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (inf instanceof LineaInvestigacion linea) {
            if (linea.getNombreInv() == null || linea.getNombreInv().isBlank()) {
                JOptionPane.showMessageDialog(null, "NOMBRE INVÁLIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        if (inf instanceof Modalidad mod) {
            if (mod.getNombre() == null || mod.getNombre().isBlank()) {
                JOptionPane.showMessageDialog(null, "NOMBRE INVÁLIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return infos.add(inf);
    }

    // ✅ READ
    public Informacion readInformacion(long id) {
        return infos.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ✅ UPDATE
    public boolean updateInformacion(Informacion nueva) {

        for (Informacion i : infos) {

            if (i.getId() == nueva.getId()) {

                if (i instanceof LineaInvestigacion li && nueva instanceof LineaInvestigacion nuevaLi) {
                    li.setNombreInv(nuevaLi.getNombreInv());
                }

                if (i instanceof Modalidad mo && nueva instanceof Modalidad nuevaMo) {
                    mo.setNombre(nuevaMo.getNombre());
                }

                return true;
            }
        }

        return false;
    }

    // ✅ DELETE
    public boolean deleteInformacion(long id) {
        return infos.removeIf(i -> i.getId() == id);
    }

    // ✅ VALIDAR ID
    private boolean existeId(long id) {
        if (id <= 0) return true;
        return infos.stream().anyMatch(i -> i.getId() == id);
    }

    // ✅ LISTAR LINEAS
    public List<LineaInvestigacion> enviarLineaInvestigacion() {

        List<LineaInvestigacion> lineas = new ArrayList<>();

        for (Informacion i : infos) {
            if (i instanceof LineaInvestigacion li) {
                lineas.add(li);
            }
        }

        return lineas;
    }

    // ✅ LISTAR MODALIDADES
    public List<Modalidad> enviarModalidad() {

        List<Modalidad> modalidades = new ArrayList<>();

        for (Informacion i : infos) {
            if (i instanceof Modalidad mo) {
                modalidades.add(mo);
            }
        }

        return modalidades;
    }

    // ✅ REPORTE
    public String reporteModalidades() {

        StringBuilder reporte = new StringBuilder();

        for (Informacion i : infos) {
            if (i instanceof Modalidad) {
                reporte.append("Nombre Modalidad: ")
                        .append(i.toString())
                        .append("\n");
            }
        }

        return reporte.toString();
    }

    // ✅ GENERAR ID
    public int generarId() {
        return infos.size() + 1;
    }
}