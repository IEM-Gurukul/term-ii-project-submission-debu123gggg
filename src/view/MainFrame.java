package view;

import controller.EventController;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final Color BG      = new Color(15, 23, 42);
    private static final Color SIDEBAR = new Color(8, 14, 28);
    private static final Color TEXT    = new Color(226, 232, 240);
    private static final Color ACCENT  = new Color(99, 102, 241);
    private static final Color SUBTEXT = new Color(148, 163, 184);

    public MainFrame(EventController controller) {
        super("EventFlow — Event Management System");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());

        // ── Sidebar ──────────────────────────────────────────────────────────
        JPanel sidebar = buildSidebar();
        add(sidebar, BorderLayout.WEST);

        // ── Main content split ───────────────────────────────────────────────
        EventTablePanel tablePanel = new EventTablePanel(controller);
        EventFormPanel  formPanel  = new EventFormPanel(controller, tablePanel);
        tablePanel.setFormPanel(formPanel);

        // Seed & initial load
        controller.loadSampleData();
        tablePanel.refreshTable();

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);
        split.setDividerLocation(420);
        split.setDividerSize(4);
        split.setContinuousLayout(true);
        split.setBackground(BG);
        split.setBorder(null);

        add(split, BorderLayout.CENTER);

        // ── Status bar ───────────────────────────────────────────────────────
        add(buildStatusBar(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel buildSidebar() {
        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(220, 0));
        side.setBackground(SIDEBAR);
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(new MatteBorder(0, 0, 0, 1, new Color(30, 41, 59)));

        // Logo
        JPanel logo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logo.setBackground(SIDEBAR);
        logo.setBorder(new EmptyBorder(30, 0, 20, 0));
        JLabel iconLbl = new JLabel("⬡");
        iconLbl.setForeground(ACCENT);
        iconLbl.setFont(new Font("Segoe UI", Font.BOLD, 32));
        logo.add(iconLbl);
        side.add(logo);

        JLabel appName = new JLabel("EventFlow");
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);
        appName.setForeground(TEXT);
        appName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        side.add(appName);

        JLabel tagline = new JLabel("Management System");
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);
        tagline.setForeground(SUBTEXT);
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        side.add(tagline);

        side.add(Box.createVerticalStrut(30));

        // Nav items
        String[][] nav = {
            {"📋", "Events"},
            {"➕", "Add Event"},
            {"🔍", "Search"},
            {"📊", "Statistics"},
        };
        for (String[] item : nav) {
            JPanel navItem = buildNavItem(item[0], item[1], item[1].equals("Events"));
            side.add(navItem);
        }

        side.add(Box.createVerticalGlue());

        // Version info
        JLabel ver = new JLabel("v1.0.0  •  MVC Architecture");
        ver.setAlignmentX(Component.CENTER_ALIGNMENT);
        ver.setForeground(new Color(71, 85, 105));
        ver.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        side.add(ver);
        side.add(Box.createVerticalStrut(20));

        return side;
    }

    private JPanel buildNavItem(String icon, String label, boolean active) {
        Color bg    = active ? new Color(30, 32, 80) : SIDEBAR;
        Color fg    = active ? TEXT : SUBTEXT;
        Color left  = active ? ACCENT : SIDEBAR;

        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
        item.setBackground(bg);
        item.setMaximumSize(new Dimension(220, 48));
        item.setBorder(new MatteBorder(0, 3, 0, 0, left));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel ico = new JLabel(icon);
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        ico.setForeground(fg);
        item.add(ico);

        JLabel lbl = new JLabel(label);
        lbl.setForeground(fg);
        lbl.setFont(new Font("Segoe UI", active ? Font.BOLD : Font.PLAIN, 13));
        item.add(lbl);

        return item;
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(SIDEBAR);
        bar.setBorder(new MatteBorder(1, 0, 0, 0, new Color(30, 41, 59)));
        bar.setPreferredSize(new Dimension(0, 28));

        JLabel left = new JLabel("  Java Swing  |  MVC Architecture  |  EventFlow v1.0");
        left.setForeground(new Color(71, 85, 105));
        left.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        JLabel right = new JLabel("© 2025 EventFlow  ");
        right.setForeground(new Color(71, 85, 105));
        right.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        bar.add(left, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }
}