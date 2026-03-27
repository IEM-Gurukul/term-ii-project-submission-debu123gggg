package view;

import controller.EventController;
import model.Event;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class EventTablePanel extends JPanel {

    private final EventController controller;
    private EventFormPanel formPanel; // set after construction

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JLabel lblStats;

    private static final Color BG         = new Color(15, 23, 42);
    private static final Color CARD       = new Color(30, 41, 59);
    private static final Color ACCENT     = new Color(99, 102, 241);
    private static final Color DANGER     = new Color(239, 68, 68);
    private static final Color DANGER_HOV = new Color(220, 38, 38);
    private static final Color WARN       = new Color(234, 179, 8);
    private static final Color WARN_HOV   = new Color(202, 154, 0);
    private static final Color TEXT       = new Color(226, 232, 240);
    private static final Color SUBTEXT    = new Color(148, 163, 184);
    private static final Color FIELD_BG   = new Color(51, 65, 85);
    private static final Color BORDER_CLR = new Color(71, 85, 105);
    private static final Color ROW_ODD    = new Color(30, 41, 59);
    private static final Color ROW_EVEN   = new Color(22, 32, 48);
    private static final Color ROW_SEL    = new Color(49, 52, 130);
    private static final Color HEADER_BG  = new Color(15, 23, 42);

    private static final String[] COLUMNS = {"ID","Event Name","Category","Date","Venue","Capacity","Status"};

    public EventTablePanel(EventController controller) {
        this.controller = controller;
        initUI();
    }

    public void setFormPanel(EventFormPanel formPanel) {
        this.formPanel = formPanel;
    }

    private void initUI() {
        setBackground(BG);
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(20, 10, 20, 20));

        add(buildTopBar(),   BorderLayout.NORTH);
        add(buildTable(),    BorderLayout.CENTER);
        add(buildActionBar(), BorderLayout.SOUTH);
    }

    // ─── Top Bar ─────────────────────────────────────────────────────────────

    private JPanel buildTopBar() {
        JPanel top = new JPanel(new BorderLayout(10, 0));
        top.setBackground(BG);
        top.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel title = new JLabel("📋  Event List");
        title.setForeground(TEXT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        top.add(title, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        right.setBackground(BG);

        lblStats = new JLabel();
        lblStats.setForeground(SUBTEXT);
        lblStats.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        right.add(lblStats);

        txtSearch = new JTextField(16);
        txtSearch.setBackground(FIELD_BG);
        txtSearch.setForeground(TEXT);
        txtSearch.setCaretColor(TEXT);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR), new EmptyBorder(5, 10, 5, 10)));
        txtSearch.putClientProperty("JTextField.placeholderText", "Search events...");
        right.add(txtSearch);

        JButton btnSearch = smallButton("Search", ACCENT, new Color(79, 82, 221));
        btnSearch.addActionListener(e -> searchEvents());
        right.add(btnSearch);

        JButton btnReset = smallButton("Reset", BORDER_CLR, new Color(100, 116, 139));
        btnReset.addActionListener(e -> { txtSearch.setText(""); refreshTable(); });
        right.add(btnReset);

        top.add(right, BorderLayout.EAST);
        return top;
    }

    // ─── Table ───────────────────────────────────────────────────────────────

    private JScrollPane buildTable() {
        tableModel = new DefaultTableModel(COLUMNS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        table = new JTable(tableModel) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(ROW_SEL);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? ROW_EVEN : ROW_ODD);
                    // Colour-code the Status column
                    if (col == 6) {
                        String val = (String) getValueAt(row, col);
                        c.setForeground(switch (val) {
                            case "Upcoming"  -> new Color(99, 102, 241);
                            case "Completed" -> new Color(34, 197, 94);
                            case "Cancelled" -> new Color(239, 68, 68);
                            default          -> TEXT;
                        });
                    } else {
                        c.setForeground(TEXT);
                    }
                }
                return c;
            }
        };

        // Header styling
        JTableHeader hdr = table.getTableHeader();
        hdr.setBackground(HEADER_BG);
        hdr.setForeground(SUBTEXT);
        hdr.setFont(new Font("Segoe UI", Font.BOLD, 12));
        hdr.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_CLR));
        ((DefaultTableCellRenderer) hdr.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        table.setBackground(ROW_EVEN);
        table.setForeground(TEXT);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(34);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFillsViewportHeight(true);

        // Column widths
        int[] widths = {40, 200, 110, 100, 140, 80, 90};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBackground(BG);
        scroll.getViewport().setBackground(ROW_EVEN);
        scroll.setBorder(new LineBorder(BORDER_CLR));
        return scroll;
    }

    // ─── Action Bar ──────────────────────────────────────────────────────────

    private JPanel buildActionBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        bar.setBackground(BG);
        bar.setBorder(new EmptyBorder(10, 0, 0, 0));

        JButton btnEdit = smallButton("✎  Edit", WARN, WARN_HOV);
        btnEdit.addActionListener(e -> editSelected());
        bar.add(btnEdit);

        JButton btnDelete = smallButton("✕  Delete", DANGER, DANGER_HOV);
        btnDelete.addActionListener(e -> deleteSelected());
        bar.add(btnDelete);

        return bar;
    }

    // ─── Actions ─────────────────────────────────────────────────────────────

    private void editSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Select an event to edit."); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        Event event = controller.getEventById(id);
        if (event != null && formPanel != null) formPanel.loadEventForEdit(event);
    }

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row < 0) { showError("Select an event to delete."); return; }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete event ID " + id + "?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteEvent(id);
            refreshTable();
        }
    }

    private void searchEvents() {
        String kw = txtSearch.getText().trim();
        populateTable(kw.isEmpty() ? controller.getAllEvents() : controller.searchEvents(kw));
    }

    public void refreshTable() {
        populateTable(controller.getAllEvents());
        updateStats();
    }

    private void populateTable(List<Event> events) {
        tableModel.setRowCount(0);
        for (Event e : events)
            tableModel.addRow(new Object[]{
                    e.getId(), e.getName(), e.getCategory(), e.getDate(),
                    e.getVenue(), e.getCapacity(), e.getStatus()
            });
        updateStats();
    }

    private void updateStats() {
        lblStats.setText(String.format(
                "Total: %d  |  Upcoming: %d  |  Completed: %d  |  Cancelled: %d",
                controller.getTotalEvents(), controller.getUpcomingCount(),
                controller.getCompletedCount(), controller.getCancelledCount()));
    }

    // ─── Helper ──────────────────────────────────────────────────────────────

    private JButton smallButton(String text, Color bg, Color hover) {
        JButton btn = new JButton(text) {
            boolean hov = false;
            { addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent e) { hov=true; repaint(); }
                public void mouseExited (java.awt.event.MouseEvent e) { hov=false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? hover : bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(100, 32));
        return btn;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}