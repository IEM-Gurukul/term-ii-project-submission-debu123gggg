package controller;

import java.util.ArrayList;
import javax.swing.*;
import model.Event;
import view.EventView;

public class EventController {
    private EventView view;
    private ArrayList<Event> eventList;
    private int selectedRow = -1;

    public EventController(EventView view) {
        this.view = view;
        this.eventList = new ArrayList<>();

        // Button actions
        this.view.addButton.addActionListener(e -> addEvent());
        this.view.updateButton.addActionListener(e -> updateEvent());
        this.view.deleteButton.addActionListener(e -> deleteEvent());
        this.view.clearButton.addActionListener(e -> clearFields());

        // Table row selection
        this.view.eventTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRow();
            }
        });
    }

    // Add Event
    private void addEvent() {
        String name = view.nameField.getText().trim();
        String date = view.dateField.getText().trim();
        String location = view.locationField.getText().trim();

        if (name.isEmpty() || date.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill all fields!");
            return;
        }

        Event event = new Event(name, date, location);
        eventList.add(event);

        view.tableModel.addRow(new Object[]{
                event.getName(),
                event.getDate(),
                event.getLocation()
        });

        JOptionPane.showMessageDialog(view, "Event Added Successfully!");
        clearFields();
    }

    // Load selected row data into text fields
    private void loadSelectedRow() {
        selectedRow = view.eventTable.getSelectedRow();

        if (selectedRow >= 0) {
            view.nameField.setText(view.tableModel.getValueAt(selectedRow, 0).toString());
            view.dateField.setText(view.tableModel.getValueAt(selectedRow, 1).toString());
            view.locationField.setText(view.tableModel.getValueAt(selectedRow, 2).toString());
        }
    }

    // Update Event
    private void updateEvent() {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Please select an event to update!");
            return;
        }

        String name = view.nameField.getText().trim();
        String date = view.dateField.getText().trim();
        String location = view.locationField.getText().trim();

        if (name.isEmpty() || date.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill all fields!");
            return;
        }

        // Update ArrayList
        Event event = eventList.get(selectedRow);
        event.setName(name);
        event.setDate(date);
        event.setLocation(location);

        // Update JTable
        view.tableModel.setValueAt(name, selectedRow, 0);
        view.tableModel.setValueAt(date, selectedRow, 1);
        view.tableModel.setValueAt(location, selectedRow, 2);

        JOptionPane.showMessageDialog(view, "Event Updated Successfully!");
        clearFields();
    }

    // Delete Event
    private void deleteEvent() {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(view, "Please select an event to delete!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to delete this event?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            eventList.remove(selectedRow);
            view.tableModel.removeRow(selectedRow);

            JOptionPane.showMessageDialog(view, "Event Deleted Successfully!");
            clearFields();
        }
    }

    // Clear fields
    private void clearFields() {
        view.nameField.setText("");
        view.dateField.setText("");
        view.locationField.setText("");
        view.eventTable.clearSelection();
        selectedRow = -1;
    }
}
