package vista;

import controlador.ControladorActa;
import modelo.Acta;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class VActa extends JFrame {

    private final ControladorActa ctrl = new ControladorActa();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DefaultTableModel model;
    private JTable tabla;
    private JTextField txtCodigo, txtFecha, txtCodProy;
    private JTextArea txtObs;

    public VActa() {
        setTitle("Actas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(970, 600);
        setLocationRelativeTo(null);
        build();
        refresh();
    }

    private void build() {
        setLayout(new BorderLayout());

        add(DS.buildSidebar("Actas",
                () -> nav(new VProyecto()),
                () -> nav(new VPersona()),
                null,
                () -> nav(new VInformacion()),
                this::logout), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(DS.BG_PRIMARY);
        main.add(DS.topBar("Actas", "Registro y consulta de actas"), BorderLayout.NORTH);

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
        panel.setPreferredSize(new Dimension(265, 0));

        lbl(panel, "NUEVA ACTA");

        txtCodigo = DS.field("001");         addRow(panel, "Código acta",        txtCodigo);
        txtFecha  = DS.field("2024-06-15");  addRow(panel, "Fecha (yyyy-MM-dd)", txtFecha);
        txtCodProy= DS.field("ID proyecto"); addRow(panel, "Código proyecto",    txtCodProy);

        JPanel obsRow = new JPanel(new BorderLayout(0, 4));
        obsRow.setBackground(DS.BG_PRIMARY);
        obsRow.setAlignmentX(LEFT_ALIGNMENT);
        obsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        obsRow.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        JLabel obsLbl = new JLabel("OBSERVACIONES");
        obsLbl.setFont(DS.FONT_LABEL); obsLbl.setForeground(DS.TEXT_SECONDARY);
        txtObs = DS.textArea();
        obsRow.add(obsLbl, BorderLayout.NORTH);
        obsRow.add(new JScrollPane(txtObs), BorderLayout.CENTER);
        panel.add(obsRow);

        panel.add(Box.createVerticalStrut(10));

        JButton bCrear = DS.btnPrimary("Registrar acta");
        bCrear.setAlignmentX(LEFT_ALIGNMENT);
        bCrear.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        panel.add(bCrear);
        panel.add(Box.createVerticalStrut(6));

        JButton bOrd = DS.btnGhost("Ver por fecha ↓");
        bOrd.setAlignmentX(LEFT_ALIGNMENT);
        bOrd.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        panel.add(bOrd);
        panel.add(Box.createVerticalStrut(6));

        JButton bLimp = DS.btnGhost("Limpiar");
        bLimp.setAlignmentX(LEFT_ALIGNMENT);
        bLimp.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        panel.add(bLimp);

        bCrear.addActionListener(e -> crear());
        bOrd.addActionListener(e -> DS.mostrarReporte(this, ctrl.ordenarActasPorFecha(), "Actas por fecha"));
        bLimp.addActionListener(e -> limpiar());

        return panel;
    }

    private JPanel buildTable() {
        model = new DefaultTableModel(new String[]{"Cód. Acta","Fecha","Cód. Proyecto","Observaciones"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = DS.styledTable(model);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(280);
        tabla.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) cargarDesdeTabla(); });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DS.BG_PRIMARY);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel stat = new JPanel(new GridLayout(1, 1, 0, 0));
        stat.setBackground(DS.BG_PRIMARY);
        stat.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));
        stat.add(DS.statCard(String.valueOf(ctrl.getActas().size()), "Actas registradas"));
        panel.add(stat, BorderLayout.NORTH);
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
            String cod = DS.val(txtCodigo), fec = DS.val(txtFecha), cp = DS.val(txtCodProy), obs = txtObs.getText().trim();
            if (cod.isEmpty() || fec.isEmpty() || cp.isEmpty()) { DS.mostrarError(this, "Completa los campos obligatorios."); return; }
            Acta a = new Acta(Long.parseLong(cod), LocalDate.parse(fec, fmt), obs, Long.parseLong(cp));
            if (ctrl.createActa(a)) { DS.mostrarOk(this, "Acta registrada."); refresh(); limpiar(); }
            else DS.mostrarError(this, "Código duplicado o datos inválidos.");
        } catch (Exception ex) { DS.mostrarError(this, "Error: " + ex.getMessage()); }
    }

    private void refresh() {
        model.setRowCount(0);
        for (Acta a : ctrl.getActas())
            model.addRow(new Object[]{ a.getCodigoActa(), a.getFecha(), a.getCodigoProyecto(), a.getObservaciones() });
    }

    private void cargarDesdeTabla() {
        int row = tabla.getSelectedRow();
        if (row < 0) return;
        setText(txtCodigo,  model.getValueAt(row, 0).toString());
        setText(txtFecha,   model.getValueAt(row, 1).toString());
        setText(txtCodProy, model.getValueAt(row, 2).toString());
        txtObs.setText(model.getValueAt(row, 3).toString());
    }

    private void setText(JTextField f, String v) { f.setForeground(DS.TEXT_PRIMARY); f.setText(v); }
    private void limpiar() { for (JTextField f : new JTextField[]{txtCodigo,txtFecha,txtCodProy}) f.setText(""); txtObs.setText(""); tabla.clearSelection(); }
    private void nav(JFrame f) { dispose(); f.setVisible(true); }
    private void logout() { if (DS.confirmar(this, "¿Cerrar sesión?")) { dispose(); new VLogin().setVisible(true); } }
}
