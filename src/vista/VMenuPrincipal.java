package vista;

import javax.swing.*;
import java.awt.*;

public class VMenuPrincipal extends JFrame {

    public VMenuPrincipal() {
        setTitle("Sistema de Proyectos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(780, 520);
        setLocationRelativeTo(null);
        build();
    }

    private void build() {
        setLayout(new BorderLayout());

        JPanel sidebar = DS.buildSidebar("",
                () -> { dispose(); new VProyecto().setVisible(true); },
                () -> { dispose(); new VPersona().setVisible(true); },
                () -> { dispose(); new VActa().setVisible(true); },
                () -> { dispose(); new VInformacion().setVisible(true); },
                this::logout);
        add(sidebar, BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(DS.BG_PRIMARY);
        main.add(DS.topBar("Menú principal", "Selecciona un módulo"), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(DS.BG_PRIMARY);
        content.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JPanel grid = new JPanel(new GridLayout(2, 2, 16, 16));
        grid.setBackground(DS.BG_PRIMARY);

        grid.add(moduleCard("Proyectos", "Crear, editar y gestionar proyectos académicos",
                () -> { dispose(); new VProyecto().setVisible(true); }));
        grid.add(moduleCard("Personas", "Registrar estudiantes y profesores",
                () -> { dispose(); new VPersona().setVisible(true); }));
        grid.add(moduleCard("Actas", "Generar y consultar actas de reunión",
                () -> { dispose(); new VActa().setVisible(true); }));
        grid.add(moduleCard("Información", "Gestionar líneas de investigación y modalidades",
                () -> { dispose(); new VInformacion().setVisible(true); }));

        content.add(grid);
        main.add(content, BorderLayout.CENTER);
        add(main, BorderLayout.CENTER);
    }

    private JPanel moduleCard(String title, String desc, Runnable action) {
        JPanel card = new JPanel();
        card.setBackground(DS.BG_PRIMARY);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DS.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(22, 22, 22, 22)));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel dot = new JPanel();
        dot.setBackground(DS.ACCENT_LIGHT);
        dot.setPreferredSize(new Dimension(36, 36));
        dot.setMaximumSize(new Dimension(36, 36));
        dot.setAlignmentX(LEFT_ALIGNMENT);
        JLabel dotLbl = new JLabel(title.substring(0, 1));
        dotLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        dotLbl.setForeground(DS.ACCENT);
        dot.add(dotLbl);
        card.add(dot);
        card.add(Box.createVerticalStrut(14));

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 14));
        t.setForeground(DS.TEXT_PRIMARY);
        t.setAlignmentX(LEFT_ALIGNMENT);
        card.add(t);
        card.add(Box.createVerticalStrut(4));

        JLabel d = new JLabel("<html><p style='width:160px'>" + desc + "</p></html>");
        d.setFont(DS.FONT_SMALL);
        d.setForeground(DS.TEXT_SECONDARY);
        d.setAlignmentX(LEFT_ALIGNMENT);
        card.add(d);
        card.add(Box.createVerticalGlue());
        card.add(Box.createVerticalStrut(14));

        JButton btn = DS.btnPrimary("Abrir →");
        btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.addActionListener(e -> action.run());
        card.add(btn);

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { card.setBackground(DS.BG_SECONDARY); dot.setBackground(DS.ACCENT); dotLbl.setForeground(Color.WHITE); }
            public void mouseExited(java.awt.event.MouseEvent e)  { card.setBackground(DS.BG_PRIMARY); dot.setBackground(DS.ACCENT_LIGHT); dotLbl.setForeground(DS.ACCENT); }
            public void mouseClicked(java.awt.event.MouseEvent e) { action.run(); }
        });

        return card;
    }

    private void logout() {
        if (DS.confirmar(this, "¿Cerrar sesión?")) { dispose(); new VLogin().setVisible(true); }
    }
}
