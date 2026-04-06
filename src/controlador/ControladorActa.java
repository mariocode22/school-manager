package controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.Acta;

public class ControladorActa {

    private final List<Acta> actas = new ArrayList<>();

    // ✅ GENERAR ID
    public int generarId() {
        return actas.size() + 1;
    }

    // ✅ CREATE
    public boolean createActa(Acta acta) {

        if (acta == null) return false;

        if (existeCodigo(acta.getCodigoActa())) {
            return false;
        }

        if (acta.getCodigoActa() <= 0 || acta.getCodigoProyecto() <= 0) {
            return false;
        }

        return actas.add(acta);
    }

    // ✅ VALIDAR EXISTENCIA
    private boolean existeCodigo(long codigoActa) {
        return actas.stream()
                .anyMatch(a -> a.getCodigoActa() == codigoActa);
    }

    // ✅ ORDENAR POR FECHA Y MOSTRAR
    public String ordenarActasPorFecha() {

        List<Acta> copia = new ArrayList<>(actas); // 🔥 evita modificar original
        Collections.sort(copia); // usa compareTo de Acta

        StringBuilder reporte = new StringBuilder();

        for (Acta a : copia) {
            reporte.append(a.toString()).append("\n");
        }

        return reporte.toString();
    }

    // ✅ LISTAR
    public List<Acta> getActas() {
        return actas;
    }
}