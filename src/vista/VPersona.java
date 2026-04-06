package vista;

import controlador.ControladorPersona;
import modelo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.Date;

public class VPersona extends JFrame {

    private final ControladorPersona ctrl = new ControladorPersona();

    private DefaultTableModel model;
    private JTable tabla;

    private JTextField txtCedula, txtNombre, txtEmail, txtCelular, txtApellidos;
    private JComboBox<String> cbTipo;

    // 🔥 NUEVO COMPONENTE
    private JSpinner spFecha;

    public VPersona() {
        setTitle("Personas");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(950, 600);
        setLocationRelativeTo(null);
        build();
        refresh();
    }

    private void build() {
        setLayout(new BorderLayout());

        add(DS.buildSidebar("Personas",
                () -> nav(new VProyecto()),
                null,
                () -> nav(new VActa()),
                () -> nav(new VInformacion()),
                this::logout), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(DS.BG_PRIMARY);
        main.add(DS.topBar("Personas", "Estudiantes y profesores"), BorderLayout.NORTH);

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
        panel.setPreferredSize(new Dimension(260, 0));

        JLabel sec = new JLabel("NUEVO REGISTRO");
        sec.setFont(DS.FONT_LABEL);
        sec.setForeground(DS.TEXT_SECONDARY);
        sec.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(sec);
        panel.add(Box.createVerticalStrut(14));

        cbTipo = DS.combo("Estudiante", "Profesor");
        addRow(panel, "Tipo", cbTipo);

        txtCedula    = DS.field("10000000");
        addRow(panel, "Cédula", txtCedula);

        txtNombre    = DS.field("Nombre completo");
        addRow(panel, "Nombre", txtNombre);

        txtEmail     = DS.field("correo@mail.com");
        addRow(panel, "Email", txtEmail);

        // 🔥 NUEVO SELECTOR DE FECHA
        spFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spFecha, "dd/MM/yyyy");
        spFecha.setEditor(editor);
        addRow(panel, "Fecha nac.", spFecha);

        txtCelular   = DS.field("3001234567");
        addRow(panel, "Celular (Est.)", txtCelular);

        txtApellidos = DS.field("Apellidos");
        addRow(panel, "Apellidos (Prof.)", txtApellidos);

        cbTipo.addActionListener(e -> toggleCampos());
        toggleCampos();

        panel.add(Box.createVerticalStrut(8));

        JButton btnCrear = DS.btnPrimary("Guardar");
        JButton btnEliminar = DS.btnDanger("Eliminar");
        JButton btnLimpiar = DS.btnGhost("Limpiar");

        btnCrear.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());

        panel.add(btnCrear);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnEliminar);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnLimpiar);

        return panel;
    }

    private JPanel buildTable() {
        model = new DefaultTableModel(new String[]{"Tipo", "Cédula", "Nombre", "Email", "Fecha nac."}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = DS.styledTable(model);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarDesdeTabla();
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DS.BG_PRIMARY);

        JButton btnAct = DS.btnGhost("Actualizar");
        btnAct.addActionListener(e -> actualizar());

        panel.add(btnAct, BorderLayout.NORTH);
        panel.add(DS.scroll(tabla), BorderLayout.CENTER);

        return panel;
    }

    private void addRow(JPanel p, String label, JComponent c) {
        JPanel row = DS.formRow(label, c);
        p.add(row);
    }

    private void toggleCampos() {
        boolean est = cbTipo.getSelectedIndex() == 0;
        txtCelular.setEnabled(est);
        txtApellidos.setEnabled(!est);
    }

    private void guardar() {
        try {
            Persona per = construir();
            if (per == null) return;

            if (ctrl.createPersona(per)) {
                DS.mostrarOk(this, "Persona creada");
                refresh();
                limpiar();
            } else {
                DS.mostrarError(this, "Error al crear");
            }
        } catch (Exception ex) {
            DS.mostrarError(this, ex.getMessage());
        }
    }

    private void actualizar() {
        Persona per = construir();
        if (per == null) return;

        if (ctrl.updatePersona(per)) {
            DS.mostrarOk(this, "Actualizado");
            refresh();
        } else {
            DS.mostrarError(this, "No encontrado");
        }
    }

    private void eliminar() {
        String ced = txtCedula.getText();

        if (ctrl.deletePersona(Long.parseLong(ced))) {
            DS.mostrarOk(this, "Eliminado");
            refresh();
            limpiar();
        } else {
            DS.mostrarError(this, "No encontrado");
        }
    }

    // 🔥 AQUÍ ESTÁ LA MAGIA DE LA FECHA
    private Persona construir() {

        long cedula = Long.parseLong(txtCedula.getText());
        String nombre = txtNombre.getText();
        String email = txtEmail.getText();

        Date date = (Date) spFecha.getValue();
        LocalDate fecha = date.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDate();

        if (cbTipo.getSelectedIndex() == 0) {
            return new Estudiante(
                    cedula,
                    nombre,
                    Long.parseLong(txtCelular.getText()),
                    email,
                    fecha
            );
        } else {
            return new Profesor(
                    cedula,
                    nombre,
                    txtApellidos.getText(),
                    email,
                    fecha
            );
        }
    }

    private void refresh() {
        model.setRowCount(0);

        for (Persona p : ctrl.getPersonas()) {
            model.addRow(new Object[]{
                    p.tipoPersona(),
                    p.getCedula(),
                    p.getNombre(),
                    p.getEmail(),
                    p.getFechaNacimiento()
            });
        }
    }

    private void cargarDesdeTabla() {
        int row = tabla.getSelectedRow();
        if (row < 0) return;

        long ced = (long) model.getValueAt(row, 1);
        Persona p = ctrl.readPersona(ced);

        txtCedula.setText(String.valueOf(p.getCedula()));
        txtNombre.setText(p.getNombre());
        txtEmail.setText(p.getEmail());

        // 🔥 CARGAR FECHA
        spFecha.setValue(java.sql.Date.valueOf(p.getFechaNacimiento()));

        if (p instanceof Estudiante e) {
            txtCelular.setText(String.valueOf(e.getCelular()));
        }

        if (p instanceof Profesor pr) {
            txtApellidos.setText(pr.getApellidos());
        }
    }

    private void limpiar() {
        txtCedula.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        txtApellidos.setText("");

        spFecha.setValue(new Date());

        tabla.clearSelection();
    }

    private void nav(JFrame f) {
        dispose();
        f.setVisible(true);
    }

    private void logout() {
        dispose();
        new VLogin().setVisible(true);
    }
}