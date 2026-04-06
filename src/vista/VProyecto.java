package vista;

import controlador.*;
import modelo.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VProyecto extends JFrame {

    private final ControladorPersona ctrlPer  = new ControladorPersona();
    private final ControladorInformacion ctrlInfo = new ControladorInformacion();
    private final ControladorProyecto ctrl;

    private DefaultTableModel model;
    private JTable tabla;
    private JTextField txtId, txtNombre, txtObjG, txtObjE, txtNota;
    private JComboBox<Modalidad> cbMod;
    private JComboBox<LineaInvestigacion> cbLinea;
    private JTextField txtCedVinc;
    private JComboBox<String> cbTipoVinc;
    private JComboBox<Object> cbIdVinc;
    private JLabel lblStats1, lblStats2, lblStats3;

    public VProyecto() {
        ctrl = new ControladorProyecto(ctrlPer);
        setTitle("Proyectos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1050, 640);
        setLocationRelativeTo(null);
        cargarInfoEjemplo();
        build();
        refresh();
    }

    private void cargarInfoEjemplo() {
        ctrlInfo.crearInformacion(new Modalidad("Trabajo de Grado", 1));
        ctrlInfo.crearInformacion(new Modalidad("Semillero", 2));
        ctrlInfo.crearInformacion(new LineaInvestigacion("Ingeniería de Software", 3));
        ctrlInfo.crearInformacion(new LineaInvestigacion("Redes y Comunicaciones", 4));
    }

    private void build() {
        setLayout(new BorderLayout());

        add(DS.buildSidebar("Proyectos",
                null,
                () -> nav(new VPersona()),
                () -> nav(new VActa()),
                () -> nav(new VInformacion()),
                this::logout), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(DS.BG_PRIMARY);
        main.add(DS.topBar("Proyectos", "Gestión de proyectos académicos"), BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(DS.BG_PRIMARY);
        body.add(buildForm(), BorderLayout.WEST);
        body.add(buildRightPanel(), BorderLayout.CENTER);
        main.add(body, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }

    private JPanel buildForm() {
        JPanel panel = new JPanel();
        panel.setBackground(DS.BG_PRIMARY);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, DS.BORDER_COLOR),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        panel.setPreferredSize(new Dimension(270, 0));

        lbl(panel, "PROYECTO");

        txtId    = DS.field("001");      addRow(panel, "ID",              txtId);
        txtNombre= DS.field("Nombre");   addRow(panel, "Nombre",          txtNombre);
        txtObjG  = DS.field("Obj. gral"); addRow(panel, "Obj. General",   txtObjG);
        txtObjE  = DS.field("Obj. esp."); addRow(panel, "Obj. Específico",txtObjE);
        txtNota  = DS.field("0.0 – 5.0"); addRow(panel, "Nota",           txtNota);

        cbMod = new JComboBox<>();
        cbLinea = new JComboBox<>();
        recargarCombos();
        addRow(panel, "Modalidad", cbMod);
        addRow(panel, "Línea",     cbLinea);

        panel.add(Box.createVerticalStrut(6));
        JButton bCrear = DS.btnPrimary("Guardar proyecto");
        bCrear.setAlignmentX(LEFT_ALIGNMENT);
        bCrear.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        panel.add(bCrear);
        panel.add(Box.createVerticalStrut(5));

        JPanel rowBtns = new JPanel(new GridLayout(1, 2, 6, 0));
        rowBtns.setBackground(DS.BG_PRIMARY);
        rowBtns.setAlignmentX(LEFT_ALIGNMENT);
        rowBtns.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        JButton bAct = DS.btnGhost("Actualizar");
        JButton bDel = DS.btnDanger("Eliminar");
        rowBtns.add(bAct); rowBtns.add(bDel);
        panel.add(rowBtns);
        panel.add(Box.createVerticalStrut(12));

        lbl(panel, "VINCULAR PERSONA");

        cbIdVinc   = new JComboBox<>();
        cbTipoVinc = DS.combo("Estudiante", "Profesor");
        txtCedVinc = DS.field("Cédula persona");
        addRow(panel, "Proyecto ID", cbIdVinc);
        addRow(panel, "Tipo",        cbTipoVinc);
        addRow(panel, "Cédula",      txtCedVinc);

        JPanel rowVinc = new JPanel(new GridLayout(1, 2, 6, 0));
        rowVinc.setBackground(DS.BG_PRIMARY);
        rowVinc.setAlignmentX(LEFT_ALIGNMENT);
        rowVinc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        JButton bVinc = DS.btnPrimary("Vincular");
        JButton bDesv = DS.btnGhost("Desvincular");
        rowVinc.add(bVinc); rowVinc.add(bDesv);
        panel.add(rowVinc);

        bCrear.addActionListener(e -> crear());
        bAct.addActionListener(e -> actualizar());
        bDel.addActionListener(e -> eliminar());
        bVinc.addActionListener(e -> vincular(true));
        bDesv.addActionListener(e -> vincular(false));

        return panel;
    }

    private JPanel buildRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DS.BG_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Stats row
        JPanel stats = new JPanel(new GridLayout(1, 3, 12, 0));
        stats.setBackground(DS.BG_PRIMARY);
        stats.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        lblStats1 = new JLabel("0"); lblStats1.setFont(DS.FONT_STAT); lblStats1.setForeground(DS.TEXT_PRIMARY);
        lblStats2 = new JLabel("0"); lblStats2.setFont(DS.FONT_STAT); lblStats2.setForeground(DS.TEXT_PRIMARY);
        lblStats3 = new JLabel("–"); lblStats3.setFont(DS.FONT_STAT); lblStats3.setForeground(DS.TEXT_PRIMARY);
        stats.add(DS.statCard("0", "Total proyectos"));
        stats.add(DS.statCard("0", "Nota ≤ 3"));
        stats.add(DS.statCard("–", "Promedio nota"));
        panel.add(stats, BorderLayout.NORTH);

        // Toolbar
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        toolbar.setBackground(DS.BG_PRIMARY);
        toolbar.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        JButton bR1 = DS.btnGhost("Reporte nota ≤ 3");
        JButton bR2 = DS.btnGhost("Reporte general");
        bR1.addActionListener(e -> DS.mostrarReporte(this, ctrl.reporteN3(), "Proyectos con nota ≤ 3"));
        bR2.addActionListener(e -> DS.mostrarReporte(this, ctrl.reportes(), "Reporte general"));
        toolbar.add(bR1); toolbar.add(bR2);
        panel.add(toolbar, BorderLayout.CENTER);

        // Tabla
        model = new DefaultTableModel(new String[]{"ID","Nombre","Modalidad","Línea","Nota"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = DS.styledTable(model);
        tabla.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) cargarDesdeTabla(); });
        panel.add(DS.scroll(tabla), BorderLayout.SOUTH);
        panel.setLayout(new BorderLayout());
        JPanel top2 = new JPanel(new BorderLayout());
        top2.setBackground(DS.BG_PRIMARY);
        top2.add(stats, BorderLayout.NORTH);
        top2.add(toolbar, BorderLayout.SOUTH);
        panel.add(top2, BorderLayout.NORTH);
        panel.add(DS.scroll(tabla), BorderLayout.CENTER);
        return panel;
    }

    private void lbl(JPanel p, String text) {
        JLabel l = new JLabel(text);
        l.setFont(DS.FONT_LABEL);
        l.setForeground(DS.TEXT_SECONDARY);
        l.setAlignmentX(LEFT_ALIGNMENT);
        p.add(l);
        p.add(Box.createVerticalStrut(10));
    }

    private void addRow(JPanel p, String label, JComponent c) {
        JPanel row = DS.formRow(label, c);
        row.setAlignmentX(LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        p.add(row);
    }

    private void recargarCombos() {
        cbMod.removeAllItems();
        for (Modalidad m : ctrlInfo.enviarModalidad()) cbMod.addItem(m);
        cbLinea.removeAllItems();
        for (LineaInvestigacion l : ctrlInfo.enviarLineaInvestigacion()) cbLinea.addItem(l);
        if (cbIdVinc != null) {
            cbIdVinc.removeAllItems();
            for (Proyecto p : ctrl.enviarProyectos()) cbIdVinc.addItem(p.getId());
        }
    }

    private void crear() {
        try {
            Proyecto p = construir();
            if (p == null) return;
            if (ctrl.createProyecto(p)) { DS.mostrarOk(this, "Proyecto creado."); refresh(); limpiar(); }
        } catch (Exception ex) { DS.mostrarError(this, "Error: " + ex.getMessage()); }
    }

    private void actualizar() {
        try {
            Proyecto p = construir();
            if (p == null) return;
            if (ctrl.updateProyecto(p)) { DS.mostrarOk(this, "Proyecto actualizado."); refresh(); limpiar(); }
            else DS.mostrarError(this, "No encontrado.");
        } catch (Exception ex) { DS.mostrarError(this, "Error: " + ex.getMessage()); }
    }

    private void eliminar() {
        String id = DS.val(txtId);
        if (id.isEmpty()) { DS.mostrarError(this, "Selecciona un proyecto."); return; }
        if (DS.confirmar(this, "¿Eliminar proyecto " + id + "?")) {
            if (ctrl.deleteProyecto(Long.parseLong(id))) { DS.mostrarOk(this, "Eliminado."); refresh(); limpiar(); }
            else DS.mostrarError(this, "No encontrado.");
        }
    }

    private void vincular(boolean vincular) {
        if (cbIdVinc.getSelectedItem() == null) return;
        try {
            long idP = (long) cbIdVinc.getSelectedItem();
            long ced = Long.parseLong(DS.val(txtCedVinc));
            boolean ok = cbTipoVinc.getSelectedIndex() == 0
                    ? (vincular ? ctrl.vincularEstudiante(idP, ced) : ctrl.desvincularEstudiante(idP, ced))
                    : (vincular ? ctrl.vincularProfesor(idP, ced)   : ctrl.desvincularProfesor(idP, ced));
            DS.mostrarOk(this, ok ? (vincular ? "Vinculado." : "Desvinculado.") : "Operación fallida. Verifica los datos.");
        } catch (Exception ex) { DS.mostrarError(this, "Error: " + ex.getMessage()); }
    }

    private Proyecto construir() {
        String id = DS.val(txtId), nom = DS.val(txtNombre);
        String og = DS.val(txtObjG), oe = DS.val(txtObjE), nota = DS.val(txtNota);
        if (id.isEmpty() || nom.isEmpty() || og.isEmpty() || oe.isEmpty() || nota.isEmpty()) {
            DS.mostrarError(this, "Completa todos los campos."); return null;
        }
        return new Proyecto(Long.parseLong(id), nom, og, oe,
                (Modalidad) cbMod.getSelectedItem(),
                (LineaInvestigacion) cbLinea.getSelectedItem(),
                null, null, Double.parseDouble(nota));
    }

    private void refresh() {
        model.setRowCount(0);
        List<Proyecto> list = ctrl.enviarProyectos();
        long bajas = list.stream().filter(p -> p.getNota() <= 3).count();
        double prom = list.stream().mapToDouble(Proyecto::getNota).average().orElse(0);
        // Actualizar stat cards (simples labels en los paneles)
        for (Proyecto p : list) {
            model.addRow(new Object[]{ p.getId(), p.getNombreProyecto(),
                    p.getModalidad() != null ? p.getModalidad().getNombre() : "–",
                    p.getLinea()     != null ? p.getLinea().getNombreInv()  : "–",
                    String.format("%.1f", p.getNota()) });
        }
        recargarCombos();
    }

    private void cargarDesdeTabla() {
        int row = tabla.getSelectedRow();
        if (row < 0) return;
        long id = Long.parseLong(model.getValueAt(row, 0).toString());
        Proyecto p = ctrl.readProyecto(id);
        if (p == null) return;
        setText(txtId,    String.valueOf(p.getId()));
        setText(txtNombre,p.getNombreProyecto());
        setText(txtObjG,  p.getObjetivoGeneral());
        setText(txtObjE,  p.getObjetivoEspecifico());
        setText(txtNota,  String.valueOf(p.getNota()));
        if (p.getModalidad() != null) cbMod.setSelectedItem(p.getModalidad());
        if (p.getLinea()     != null) cbLinea.setSelectedItem(p.getLinea());
    }

    private void setText(JTextField f, String v) { f.setForeground(DS.TEXT_PRIMARY); f.setText(v); }
    private void limpiar() { for (JTextField f : new JTextField[]{txtId,txtNombre,txtObjG,txtObjE,txtNota}) f.setText(""); tabla.clearSelection(); }
    private void nav(JFrame f) { dispose(); f.setVisible(true); }
    private void logout() { if (DS.confirmar(this, "¿Cerrar sesión?")) { dispose(); new VLogin().setVisible(true); } }
}
