package view;

import controller.EventController;
import model.Event;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventFormPanel extends JPanel {

    private final EventController controller;
    private final EventTablePanel tablePanel;

    // Form fields
    private JTextField txtName, txtVenue, txtCapacity, txtDate;
    private JComboBox<String> cmbCategory, cmbStatus;
    private JTextArea txtDescription;
    private JLabel lblFormTitle;
    private JButton btnSave, btnClear;

    private int editingId = -1; // -1 means "add mode"

    private static final Color BG          = new Color(15, 23, 42);
    private static final Color CARD        = new Color(30, 41, 59);
    private static final Color ACCENT      = new Color(99, 102, 241);
    private static final Color ACCENT_HOV  = new Color(79, 82, 221);
    private static final Color TEXT        = new Color(226, 232, 240);
    private static final Color SUBTEXT     = new Color(148, 163, 184);
    private static final Color FIELD_BG    = new Color(51, 65, 85);
    private static final Color BORDER_CLR  = new Color(71, 85, 105);
    private static final Color SUCCESS     = new Color(34, 197, 94);
    private static final Color SUCCESS_HOV = new Color(22, 163, 74);

    public EventFormPanel(EventController controller, EventTablePanel tablePanel) {
        this.controller = controller;
        this.tablePanel = tablePanel;
        initUI();
    }

    private void initUI() {
        setBackground(BG);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(20, 20, 20, 10));

        // ── Header ──────────────────────────────────────────────────────────
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(BG);
        lblFormTitle = new JLabel("✦  Add New Event");
        lblFormTitle.setForeground(TEXT);
        lblFormTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.add(lblFormTitle);
        add(header, BorderLayout.NORTH);

        // ── Form card ────────────────────────────────────────────────────────
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(20, 20, 20, 20)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 4, 6, 4);

        int row = 0;

        // Event Name
        addLabel(card, gbc, "Event Name *", row, 0);
        txtName = styledField("");
        addField(card, gbc, txtName, row, 1, 3);
        row++;

        // Category | Date
        addLabel(card, gbc, "Category *", row, 0);
        cmbCategory = styledCombo(new String[]{"Conference","Music","Exhibition","Networking","Festival","Sports","Workshop","Other"});
        addField(card, gbc, cmbCategory, row, 1, 1);

        addLabel(card, gbc, "Date (YYYY-MM-DD) *", row, 2);
        txtDate = styledField("2025-12-01");
        addField(card, gbc, txtDate, row, 3, 1);
        row++;

        // Venue | Capacity
        addLabel(card, gbc, "Venue *", row, 0);
        txtVenue = styledField("");
        addField(card, gbc, txtVenue, row, 1, 1);

        addLabel(card, gbc, "Capacity *", row, 2);
        txtCapacity = styledField("100");
        addField(card, gbc, txtCapacity, row, 3, 1);
        row++;

        // Status (only visible in edit mode initially hidden)
        addLabel(card, gbc, "Status", row, 0);
        cmbStatus = styledCombo(new String[]{"Upcoming","Completed","Cancelled"});
        addField(card, gbc, cmbStatus, row, 1, 1);
        row++;

        // Description
        addLabel(card, gbc, "Description", row, 0);
        txtDescription = new JTextArea(3, 20);
        txtDescription.setBackground(FIELD_BG);
        txtDescription.setForeground(TEXT);
        txtDescription.setCaretColor(TEXT);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescription.setBorder(new EmptyBorder(8, 10, 8, 10));
        txtDescription.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setBorder(new LineBorder(BORDER_CLR));
        gbc.gridx = 1; gbc.gridy = row;
        gbc.gridwidth = 3; gbc.weightx = 1;
        card.add(scrollDesc, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0;
        row++;

        // ── Buttons ─────────────────────────────────────────────────────────
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setBackground(CARD);
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 4;
        card.add(btnPanel, gbc);

        btnClear = styledButton("✕  Clear", BORDER_CLR, new Color(71, 85, 105));
        btnClear.addActionListener(e -> clearForm());
        btnPanel.add(btnClear);

        btnSave = styledButton("＋  Save Event", ACCENT, ACCENT_HOV);
        btnSave.addActionListener(e -> saveEvent());
        btnPanel.add(btnSave);

        add(card, BorderLayout.CENTER);
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private void addLabel(JPanel p, GridBagConstraints gbc, String text, int row, int col) {
        gbc.gridx = col; gbc.gridy = row; gbc.weightx = 0;
        JLabel lbl = new JLabel(text);
        lbl.setForeground(SUBTEXT);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        p.add(lbl, gbc);
    }

    private void addField(JPanel p, GridBagConstraints gbc, JComponent c, int row, int col, int span) {
        gbc.gridx = col; gbc.gridy = row;
        gbc.gridwidth = span; gbc.weightx = 1;
        p.add(c, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0;
    }

    private JTextField styledField(String placeholder) {
        JTextField f = new JTextField(placeholder);
        f.setBackground(FIELD_BG);
        f.setForeground(TEXT);
        f.setCaretColor(TEXT);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR), new EmptyBorder(6, 10, 6, 10)));
        return f;
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> c = new JComboBox<>(items);
        c.setBackground(FIELD_BG);
        c.setForeground(TEXT);
        c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        c.setBorder(new LineBorder(BORDER_CLR));
        return c;
    }

    private JButton styledButton(String text, Color bg, Color hover) {
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 36));
        return btn;
    }

    // ─── Actions ──────────────────────────────────────────────────────────────

    private void saveEvent() {
        String name     = txtName.getText().trim();
        String category = (String) cmbCategory.getSelectedItem();
        String dateStr  = txtDate.getText().trim();
        String venue    = txtVenue.getText().trim();
        String capStr   = txtCapacity.getText().trim();
        String desc     = txtDescription.getText().trim();
        String status   = (String) cmbStatus.getSelectedItem();

        // Validation
        if (name.isEmpty() || venue.isEmpty() || dateStr.isEmpty() || capStr.isEmpty()) {
            showError("Please fill in all required (*) fields.");
            return;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException ex) {
            showError("Invalid date format. Use YYYY-MM-DD.");
            return;
        }

        int capacity;
        try {
            capacity = Integer.parseInt(capStr);
            if (capacity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            showError("Capacity must be a positive integer.");
            return;
        }

        if (editingId == -1) {
            controller.addEvent(name, category, date, venue, capacity, desc);
            showSuccess("Event added successfully!");
        } else {
            controller.updateEvent(editingId, name, category, date, venue, capacity, desc, status);
            showSuccess("Event updated successfully!");
            editingId = -1;
        }

        clearForm();
        tablePanel.refreshTable();
    }

    public void loadEventForEdit(Event event) {
        editingId = event.getId();
        lblFormTitle.setText("✎  Edit Event  (ID: " + editingId + ")");
        btnSave.setText("✔  Update Event");

        txtName.setText(event.getName());
        cmbCategory.setSelectedItem(event.getCategory());
        txtDate.setText(event.getDate().toString());
        txtVenue.setText(event.getVenue());
        txtCapacity.setText(String.valueOf(event.getCapacity()));
        txtDescription.setText(event.getDescription());
        cmbStatus.setSelectedItem(event.getStatus());
    }

    private void clearForm() {
        editingId = -1;
        lblFormTitle.setText("✦  Add New Event");
        btnSave.setText("＋  Save Event");
        txtName.setText("");
        cmbCategory.setSelectedIndex(0);
        txtDate.setText("2025-12-01");
        txtVenue.setText("");
        txtCapacity.setText("100");
        txtDescription.setText("");
        cmbStatus.setSelectedIndex(0);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}