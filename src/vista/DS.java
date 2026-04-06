package vista;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public final class DS {

    public static final Color SIDEBAR_BG     = new Color(0x1B1F2B);
    public static final Color SIDEBAR_TEXT   = new Color(0xFFFFFF);
    public static final Color SIDEBAR_MUTED  = new Color(0x8A8EA0);
    public static final Color ACCENT         = new Color(0x534AB7);
    public static final Color ACCENT_HOVER   = new Color(0x3C3489);
    public static final Color ACCENT_LIGHT   = new Color(0xEEEDFE);
    public static final Color ACCENT_TEXT    = new Color(0x534AB7);
    public static final Color TEAL_BG        = new Color(0xE1F5EE);
    public static final Color TEAL_TEXT      = new Color(0x0F6E56);
    public static final Color AMBER_BG       = new Color(0xFAEEDA);
    public static final Color AMBER_TEXT     = new Color(0x854F0B);
    public static final Color DANGER_BG      = new Color(0xFCEBEB);
    public static final Color DANGER_TEXT    = new Color(0xA32D2D);
    public static final Color DANGER_BORDER  = new Color(0xF09595);
    public static final Color BG_PRIMARY     = Color.WHITE;
    public static final Color BG_SECONDARY   = new Color(0xF7F7F5);
    public static final Color BORDER_COLOR   = new Color(0xE0E0E0);
    public static final Color TEXT_PRIMARY   = new Color(0x1A1A1A);
    public static final Color TEXT_SECONDARY = new Color(0x6B6B6B);
    public static final Color TEXT_HINT      = new Color(0xBBBBBB);
    public static final Color ROW_SELECTED   = new Color(0xEEEDFE);
    public static final Color ROW_ALT        = new Color(0xFAFAFA);

    public static final Font FONT_BODY  = new Font("SansSerif", Font.PLAIN,  13);
    public static final Font FONT_SMALL = new Font("SansSerif", Font.PLAIN,  12);
    public static final Font FONT_BOLD  = new Font("SansSerif", Font.BOLD,   13);
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD,   15);
    public static final Font FONT_LARGE = new Font("SansSerif", Font.BOLD,   20);
    public static final Font FONT_NAV   = new Font("SansSerif", Font.PLAIN,  13);
    public static final Font FONT_MONO  = new Font(Font.MONOSPACED, Font.PLAIN, 12);
    public static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD,   11);
    public static final Font FONT_STAT  = new Font("SansSerif", Font.BOLD,   22);

    private DS() {}

    public static JTextField field(String placeholder) {
        JTextField f = new JTextField();
        f.setFont(FONT_BODY);
        f.setForeground(TEXT_PRIMARY);
        f.setBackground(BG_SECONDARY);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)));
        f.setPreferredSize(new Dimension(0, 34));
        if (placeholder != null) {
            f.putClientProperty("PH", placeholder);
            f.setForeground(TEXT_HINT);
            f.setText(placeholder);
            f.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (f.getText().equals(placeholder)) { f.setText(""); f.setForeground(TEXT_PRIMARY); }
                }
                public void focusLost(FocusEvent e) {
                    if (f.getText().isBlank()) { f.setText(placeholder); f.setForeground(TEXT_HINT); }
                }
            });
        }
        return f;
    }

    public static JPasswordField passwordField() {
        JPasswordField f = new JPasswordField();
        f.setFont(FONT_BODY);
        f.setForeground(TEXT_PRIMARY);
        f.setBackground(BG_SECONDARY);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)));
        f.setPreferredSize(new Dimension(0, 34));
        return f;
    }

    public static JTextArea textArea() {
        JTextArea ta = new JTextArea(4, 15);
        ta.setFont(FONT_BODY);
        ta.setForeground(TEXT_PRIMARY);
        ta.setBackground(BG_SECONDARY);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)));
        return ta;
    }

    public static JComboBox<String> combo(String... items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_BODY);
        cb.setBackground(BG_SECONDARY);
        cb.setPreferredSize(new Dimension(0, 34));
        return cb;
    }

    public static JButton btnPrimary(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BOLD);
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(ACCENT_HOVER); }
            public void mouseExited(MouseEvent e)  { b.setBackground(ACCENT); }
        });
        return b;
    }

    public static JButton btnGhost(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BODY);
        b.setBackground(BG_PRIMARY);
        b.setForeground(TEXT_PRIMARY);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(7, 14, 7, 14)));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(BG_SECONDARY); }
            public void mouseExited(MouseEvent e)  { b.setBackground(BG_PRIMARY); }
        });
        return b;
    }

    public static JButton btnDanger(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_BOLD);
        b.setBackground(DANGER_BG);
        b.setForeground(DANGER_TEXT);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DANGER_BORDER, 1),
                BorderFactory.createEmptyBorder(7, 14, 7, 14)));
        return b;
    }

    public static JTable styledTable(DefaultTableModel model) {
        JTable t = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(ROW_SELECTED);
                    c.setForeground(ACCENT_TEXT);
                } else {
                    c.setBackground(row % 2 == 0 ? BG_PRIMARY : ROW_ALT);
                    c.setForeground(TEXT_PRIMARY);
                }
                return c;
            }
        };
        t.setFont(FONT_BODY);
        t.setRowHeight(38);
        t.setShowHorizontalLines(true);
        t.setShowVerticalLines(false);
        t.setGridColor(new Color(0xEEEEEE));
        t.setSelectionBackground(ROW_SELECTED);
        t.setSelectionForeground(ACCENT_TEXT);
        t.setFillsViewportHeight(true);
        t.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader h = t.getTableHeader();
        h.setFont(FONT_LABEL);
        h.setForeground(TEXT_SECONDARY);
        h.setBackground(BG_SECONDARY);
        h.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        h.setPreferredSize(new Dimension(0, 36));
        ((DefaultTableCellRenderer) h.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        for (int i = 0; i < t.getColumnCount(); i++) t.getColumnModel().getColumn(i).setCellRenderer(cr);
        return t;
    }

    public static JScrollPane scroll(JComponent c) {
        JScrollPane sp = new JScrollPane(c);
        sp.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        sp.getViewport().setBackground(BG_PRIMARY);
        return sp;
    }

    public static JPanel formRow(String labelText, JComponent field) {
        JPanel row = new JPanel(new BorderLayout(0, 4));
        row.setBackground(BG_PRIMARY);
        row.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        JLabel lbl = new JLabel(labelText.toUpperCase());
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_SECONDARY);
        row.add(lbl, BorderLayout.NORTH);
        row.add(field, BorderLayout.CENTER);
        return row;
    }

    public static JPanel statCard(String value, String label) {
        JPanel card = new JPanel();
        card.setBackground(BG_SECONDARY);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel val = new JLabel(value);
        val.setFont(FONT_STAT);
        val.setForeground(TEXT_PRIMARY);
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_SMALL);
        lbl.setForeground(TEXT_SECONDARY);
        card.add(val);
        card.add(Box.createVerticalStrut(2));
        card.add(lbl);
        return card;
    }

    public static JPanel topBar(String title, String subtitle) {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_PRIMARY);
        bar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(14, 22, 14, 22)));
        JPanel titles = new JPanel();
        titles.setBackground(BG_PRIMARY);
        titles.setLayout(new BoxLayout(titles, BoxLayout.Y_AXIS));
        JLabel t = new JLabel(title);
        t.setFont(FONT_TITLE);
        t.setForeground(TEXT_PRIMARY);
        JLabel s = new JLabel(subtitle);
        s.setFont(FONT_SMALL);
        s.setForeground(TEXT_SECONDARY);
        titles.add(t);
        titles.add(s);
        JPanel av = new JPanel(new BorderLayout());
        av.setPreferredSize(new Dimension(34, 34));
        av.setBackground(ACCENT_LIGHT);
        av.setBorder(BorderFactory.createLineBorder(ACCENT_LIGHT, 17));
        JLabel avLbl = new JLabel("TR", SwingConstants.CENTER);
        avLbl.setFont(FONT_BOLD);
        avLbl.setForeground(ACCENT_TEXT);
        av.add(avLbl);
        bar.add(titles, BorderLayout.WEST);
        bar.add(av,     BorderLayout.EAST);
        return bar;
    }

    public static JPanel buildSidebar(String active, Runnable onP, Runnable onPer,
                                       Runnable onA, Runnable onI, Runnable onOut) {
        JPanel sb = new JPanel();
        sb.setBackground(SIDEBAR_BG);
        sb.setLayout(new BoxLayout(sb, BoxLayout.Y_AXIS));
        sb.setPreferredSize(new Dimension(210, 0));

        JPanel logo = new JPanel();
        logo.setBackground(SIDEBAR_BG);
        logo.setLayout(new BoxLayout(logo, BoxLayout.Y_AXIS));
        logo.setBorder(BorderFactory.createEmptyBorder(22, 20, 16, 20));
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        logo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        JLabel lt = new JLabel("SGP");
        lt.setFont(new Font("SansSerif", Font.BOLD, 17));
        lt.setForeground(SIDEBAR_TEXT);
        JLabel ls = new JLabel("Sistema de Proyectos");
        ls.setFont(FONT_SMALL);
        ls.setForeground(SIDEBAR_MUTED);
        logo.add(lt); logo.add(Box.createVerticalStrut(2)); logo.add(ls);
        sb.add(logo);

        sb.add(divider());
        sb.add(Box.createVerticalStrut(6));

        String[] ns = {"Proyectos","Personas","Actas","Información"};
        Runnable[] rs = {onP, onPer, onA, onI};
        for (int i = 0; i < ns.length; i++) sb.add(navItem(sb, ns[i], ns[i].equals(active), rs[i]));

        sb.add(Box.createVerticalGlue());
        sb.add(divider());

        JPanel foot = new JPanel();
        foot.setBackground(SIDEBAR_BG);
        foot.setLayout(new BoxLayout(foot, BoxLayout.Y_AXIS));
        foot.setBorder(BorderFactory.createEmptyBorder(12, 20, 18, 20));
        foot.setAlignmentX(Component.LEFT_ALIGNMENT);
        foot.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        JLabel ses = new JLabel("Sesión activa · Trabajo");
        ses.setFont(FONT_SMALL); ses.setForeground(SIDEBAR_MUTED);
        JButton salir = new JButton("Cerrar sesión");
        salir.setFont(FONT_SMALL); salir.setForeground(new Color(0xF09595));
        salir.setBackground(SIDEBAR_BG); salir.setBorderPainted(false);
        salir.setFocusPainted(false); salir.setOpaque(false);
        salir.setHorizontalAlignment(SwingConstants.LEFT);
        salir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        salir.addActionListener(e -> onOut.run());
        foot.add(ses); foot.add(Box.createVerticalStrut(4)); foot.add(salir);
        sb.add(foot);
        return sb;
    }

    private static JPanel navItem(JPanel sb, String text, boolean active, Runnable action) {
        Color bg = active ? new Color(0x28, 0x2C, 0x3D) : SIDEBAR_BG;
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        item.setBackground(bg);
        item.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 20));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel bar = new JPanel();
        bar.setPreferredSize(new Dimension(3, 20));
        bar.setBackground(active ? ACCENT : SIDEBAR_BG);
        item.add(Box.createHorizontalStrut(0));

        JPanel dot = new JPanel();
        dot.setPreferredSize(new Dimension(7, 7));
        dot.setBackground(active ? ACCENT : new Color(0x3E4255));
        dot.setOpaque(true);

        JLabel lbl = new JLabel("  " + text);
        lbl.setFont(FONT_NAV);
        lbl.setForeground(active ? SIDEBAR_TEXT : SIDEBAR_MUTED);

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        row.setBackground(bg);
        row.add(bar); row.add(Box.createHorizontalStrut(14)); row.add(dot); row.add(lbl);
        item.add(row);

        item.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { if (action != null) action.run(); }
            public void mouseEntered(MouseEvent e) { if (!active) item.setBackground(new Color(0x23273A)); row.setBackground(new Color(0x23273A)); }
            public void mouseExited(MouseEvent e)  { if (!active) { item.setBackground(SIDEBAR_BG); row.setBackground(SIDEBAR_BG); } }
        });
        return item;
    }

    private static JSeparator divider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(0x2E3344));
        sep.setBackground(new Color(0x2E3344));
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    public static void mostrarError(Component p, String msg) {
        JOptionPane.showMessageDialog(p, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarOk(Component p, String msg) {
        JOptionPane.showMessageDialog(p, msg, "Listo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmar(Component p, String msg) {
        return JOptionPane.showConfirmDialog(p, msg, "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static void mostrarReporte(Component p, String texto, String titulo) {
        JTextArea area = new JTextArea(texto);
        area.setEditable(false);
        area.setFont(FONT_MONO);
        area.setBackground(BG_SECONDARY);
        area.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new Dimension(480, 320));
        JOptionPane.showMessageDialog(p, sp, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    public static String val(JTextField f) {
        String v = f.getText().trim();
        String ph = (String) f.getClientProperty("PH");
        return (ph != null && v.equals(ph)) ? "" : v;
    }
}
