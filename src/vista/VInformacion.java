package vista;

import controlador.ControladorInformacion;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VInformacion extends JFrame {

    private final ControladorInformacion ctrl = new ControladorInformacion();

    private DefaultTableModel model;
    private JTable tabla;
    private JTextField txtId, txtNombre;
    private JComboBox<String> cbTipo;

    public VInformacion() {
        setTitle("Información");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 560);
        setLocationRelativeTo(null);
        build();
        refresh();
    }

    private void build() {
        setLayout(new BorderLayout());

        add(DS.buildSidebar("Información",
                () -> nav(new VProyecto()),
                () -> nav(new VPersona()),
                () -> nav(new VActa()),
                null,
                this::logout), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(DS.BG_PRIMARY);
        main.add(DS.topBar("Información", "Líneas de investigación y modalidades"), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(DS.BG_PRIMARY);
        body.add(buildForm(), BorderLayout.WEST);
        body.add(buildTable(), BorderLayout.CENTER);
        main.add(body, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel();
        panel.setBackground(DS.BG_PRIMARY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, DS.BORDER_COLOR),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        panel.setPreferredSize(new Dimension(255, 0));

        lbl(panel, "REGISTRO");

        cbTipo   = DS.combo("Línea de Investigación", "Modalidad");
        txtId    = DS.field("1");
        txtNombre= DS.field("Nombre");

        addRow(panel, "Tipo",   cbTipo);
        addRow(panel, "ID",     txtId);
        addRow(panel, "Nombre", txtNombre);

        panel.add(Box.createVerticalStrut(10));

        JButton bCrear = DS.btnPrimary("Guardar");
        bCrear.setAlignmentX(LEFT_ALIGNMENT);
        bCrear.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        panel.add(bCrear);
        panel.add(Box.createVerticalStrut(6));

        JPanel row2 = new JPanel(new GridLayout(1, 2, 6, 0));
        row2.setBackground(DS.BG_PRIMARY);
        row2.setAlignmentX(LEFT_ALIGNMENT);
        row2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JButton bAct = DS.btnGhost("Actualizar");
        JButton bDel = DS.btnDanger("Eliminar");
        row2.add(bAct); row2.add(bDel);
        panel.add(row2);
        panel.add(Box.createVerticalStrut(10));

        JButton bRep = DS.btnGhost("Reporte modalidades");
        bRep.setAlignmentX(LEFT_ALIGNMENT);
        bRep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        panel.add(bRep);
        panel.add(Box.createVerticalStrut(6));

        JButton bLimp = DS.btnGhost("Limpiar");
        bLimp.setAlignmentX(LEFT_ALIGNMENT);
        bLimp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        panel.add(bLimp);

        bCrear.addActionListener(e -> crear());
        bAct.addActionListener(e -> actualizar());
        bDel.addActionListener(e -> eliminar());
        bRep.addActionListener(e -> DS.mostrarReporte(this, ctrl.reporteModalidades(), "Reporte de Modalidades"));
        bLimp.addActionListener(e -> limpiar());

        return panel;
    }

    private JPanel buildTable() {
        model = new DefaultTableModel(new String[]{"Tipo","ID","Nombre / Descripción"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = DS.styledTable(model);
        tabla.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) cargarDesdeTabla(); });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DS.BG_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel statsRow = new JPanel(new GridLayout(1, 2, 12, 0));
        statsRow.setBackground(DS.BG_PRIMARY);
        statsRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        statsRow.add(DS.statCard("0", "Líneas de investigación"));
        statsRow.add(DS.statCard("0", "Modalidades"));
        panel.add(statsRow, BorderLayout.NORTH);
        panel.add(DS.scroll(tabla), BorderLayout.CENTER);
        return panel;
    }

    private void lbl(JPanel p, String text) {
        JLabel l = new JLabel(text); l.setFont(DS.FONT_LABEL); l.setForeground(DS.TEXT_SECONDARY); l.setAlignmentX(LEFT_ALIGNMENT);
        p.add(l); p.add(Box.createVerticalStrut(10));
    }

    private void addRow(JPanel p, String label, JComponent c) {
        JPanel row = DS.formRow(label, c);
        row.setAlignmentX(LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        p.add(row);
    }

    private void crear() {
        try {
            Informacion info = construir();
            if (info == null) return;
            if (ctrl.crearInformacion(info)) { DS.mostrarOk(this, "Registrado."); refresh(); limpiar(); }
        } catch (Exception ex) { DS.mostrarError(this, "Error: " + ex.getMessage()); }
    }

    private void actualizar() {
        try {
            Informacion info = construir();
            if (info == null) return;
            if (ctrl.updateInformacion(info)) { DS.mostrarOk(this, "Actualizado."); refresh(); limpiar(); }
            else DS.mostrarError(this, "No encontrado.");
        } catch (Exception ex) { DS.mostrarError(this, "Error: " + ex.getMessage()); }
    }

    private void eliminar() {
        String id = DS.val(txtId);
        if (id.isEmpty()) { DS.mostrarError(this, "Selecciona un registro."); return; }
        if (DS.confirmar(this, "¿Eliminar registro ID " + id + "?")) {
            if (ctrl.deleteInformacion(Long.parseLong(id))) { DS.mostrarOk(this, "Eliminado."); refresh(); limpiar(); }
            else DS.mostrarError(this, "No encontrado.");
        }
    }

    private Informacion construir() {
        String id  = DS.val(txtId);
        String nom = DS.val(txtNombre);
        if (id.isEmpty() || nom.isEmpty()) { DS.mostrarError(this, "Completa ID y Nombre."); return null; }
        long lid = Long.parseLong(id);
        return cbTipo.getSelectedIndex() == 0 ? new LineaInvestigacion(nom, lid) : new Modalidad(nom, lid);
    }

    private void refresh() {
        model.setRowCount(0);
        List<LineaInvestigacion> lineas = ctrl.enviarLineaInvestigacion();
        for (LineaInvestigacion l : lineas) model.addRow(new Object[]{"Línea de Investigación", l.getId(), l.getNombreInv()});
        List<Modalidad> mods = ctrl.enviarModalidad();
        for (Modalidad m : mods) model.addRow(new Object[]{"Modalidad", m.getId(), m.getNombre()});
    }

    private void cargarDesdeTabla() {
        int row = tabla.getSelectedRow();
        if (row < 0) return;
        String tipo = model.getValueAt(row, 0).toString();
        cbTipo.setSelectedIndex(tipo.startsWith("Línea") ? 0 : 1);
        setText(txtId,     model.getValueAt(row, 1).toString());
        setText(txtNombre, model.getValueAt(row, 2).toString());
    }

    private void setText(JTextField f, String v) { f.setForeground(DS.TEXT_PRIMARY); f.setText(v); }
    private void limpiar() { txtId.setText(""); txtNombre.setText(""); tabla.clearSelection(); }
    private void nav(JFrame f) { dispose(); f.setVisible(true); }
    private void logout() { if (DS.confirmar(this, "¿Cerrar sesión?")) { dispose(); new VLogin().setVisible(true); } }
}
