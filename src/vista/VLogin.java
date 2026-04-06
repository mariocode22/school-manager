package vista;

import controlador.ControladorLogin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VLogin extends JFrame {

    private final ControladorLogin ctrl = new ControladorLogin();
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JLabel lblError;

    public VLogin() {
        setTitle("Sistema de Proyectos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(440, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(DS.BG_SECONDARY);
        build();
    }

    private void build() {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(DS.BG_SECONDARY);

        JPanel card = new JPanel();
        card.setBackground(DS.BG_PRIMARY);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DS.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(34, 38, 34, 38)));

        // Ícono
        JPanel iconBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        iconBox.setBackground(DS.ACCENT_LIGHT);
        iconBox.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        iconBox.setMaximumSize(new Dimension(52, 36));
        iconBox.setAlignmentX(LEFT_ALIGNMENT);
        JLabel iconLbl = new JLabel("SGP");
        iconLbl.setFont(new Font("SansSerif", Font.BOLD, 12));
        iconLbl.setForeground(DS.ACCENT);
        iconBox.add(iconLbl);
        card.add(iconBox);
        card.add(Box.createVerticalStrut(20));

        JLabel title = new JLabel("Bienvenido");
        title.setFont(DS.FONT_LARGE);
        title.setForeground(DS.TEXT_PRIMARY);
        title.setAlignmentX(LEFT_ALIGNMENT);
        card.add(title);
        card.add(Box.createVerticalStrut(4));

        JLabel sub = new JLabel("Ingresa tus credenciales para continuar");
        sub.setFont(DS.FONT_SMALL);
        sub.setForeground(DS.TEXT_SECONDARY);
        sub.setAlignmentX(LEFT_ALIGNMENT);
        card.add(sub);
        card.add(Box.createVerticalStrut(26));

        txtUsuario = DS.field("tu-usuario");
        JPanel fU = DS.formRow("Usuario", txtUsuario);
        fU.setAlignmentX(LEFT_ALIGNMENT);
        fU.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        card.add(fU);

        txtContrasena = DS.passwordField();
        JPanel fP = DS.formRow("Contraseña", txtContrasena);
        fP.setAlignmentX(LEFT_ALIGNMENT);
        fP.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));
        card.add(fP);
        card.add(Box.createVerticalStrut(6));

        lblError = new JLabel(" ");
        lblError.setFont(DS.FONT_SMALL);
        lblError.setForeground(DS.DANGER_TEXT);
        lblError.setAlignmentX(LEFT_ALIGNMENT);
        card.add(lblError);
        card.add(Box.createVerticalStrut(16));

        JButton btnIngresar = DS.btnPrimary("Ingresar");
        btnIngresar.setAlignmentX(LEFT_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        card.add(btnIngresar);

        card.setPreferredSize(new Dimension(350, 380));
        wrapper.add(card);
        add(wrapper);

        btnIngresar.addActionListener(e -> login());
        txtContrasena.addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) login();
            }
        });
    }

    private void login() {
        String u = DS.val(txtUsuario);
        String p = new String(txtContrasena.getPassword());
        if (u.isEmpty() || p.isEmpty()) { lblError.setText("Completa todos los campos."); return; }
        if (ctrl.login(u, p)) { dispose(); new VMenuPrincipal().setVisible(true); }
        else { lblError.setText("Usuario o contraseña incorrectos."); txtContrasena.setText(""); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VLogin().setVisible(true));
    }
}
