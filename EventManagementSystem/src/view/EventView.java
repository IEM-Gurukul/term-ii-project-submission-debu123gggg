package src.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EventView extends JFrame {

    public JTextField txtName;
    public JTextField txtDate;
    public JTextField txtLocation;

    public JButton btnAdd;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnClear;

    public JTable eventTable;
    public DefaultTableModel tableModel;

    public EventView() {
        setTitle("Event Management System");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Event Details"));

        formPanel.add(new JLabel("Event Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Event Date:"));
        txtDate = new JTextField();
        formPanel.add(txtDate);

        formPanel.add(new JLabel("Event Location:"));
        txtLocation = new JTextField();
        formPanel.add(txtLocation);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Table
        String[] columns = {"Event Name", "Event Date", "Event Location"};
        tableModel = new DefaultTableModel(columns, 0);
        eventTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Event List"));

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}
