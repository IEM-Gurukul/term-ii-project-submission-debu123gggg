package src.controller;

import src.model.Event;
import src.view.EventView;

import javax.swing.*;
import java.util.ArrayList;

public class EventController {

    private EventView view;
    private ArrayList<Event> eventList;
    private int selectedIndex = -1;

    public EventController(EventView view) {
        this.view = view;
        this.eventList = new ArrayList<>();

        // Button actions
        this.view.btnAdd.addActionListener(e -> addEvent());
        this.view.btnUpdate.addActionListener(e -> updateEvent());
        this.view.btnDelete.addActionListener(e -> deleteEvent());
        this.view.btnClear.addActionListener(e -> clearFields());

        // Table row selection
        this.view.eventTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedEvent();
            }
        });
    }

    // Add Event
    private void addEvent() {
        String name = view.txtName.getText().trim();
        String date = view.txtDate.getText().trim();
        String location = view.txtLocation.getText().trim();

        if (name.isEmpty() || date.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill all fields.");
            return;
        }

        Event event = new Event(name, date, location);
        eventList.add(event);

        view.tableModel.addRow(new Object[]{
                event.getEventName(),
                event.getEventDate(),
                event.getEventLocation()
        });

        JOptionPane.showMessageDialog(view, "Event added successfully.");
        clearFields();
    }

    // Load selected event into fields
    private void loadSelectedEvent() {
        selectedIndex = view.eventTable.getSelectedRow();

        if (selectedIndex >= 0) {
            view.txtName.setText(view.tableModel.getValueAt(selectedIndex, 0).toString());
            view.txtDate.setText(view.tableModel.getValueAt(selectedIndex, 1).toString());
            view.txtLocation.setText(view.tableModel.getValueAt(selectedIndex, 2).toString());
        }
    }

    // Update Event
    private void updateEvent() {
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(view, "Please select an event to update.");
            return;
        }

        String name = view.txtName.getText().trim();
        String date = view.txtDate.getText().trim();
        String location = view.txtLocation.getText().trim();

        if (name.isEmpty() || date.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Please fill all fields.");
            return;
        }

        // Update in ArrayList
        Event event = eventList.get(selectedIndex);
        event.setEventName(name);
        event.setEventDate(date);
        event.setEventLocation(location);

        // Update in JTable
        view.tableModel.setValueAt(name, selectedIndex, 0);
        view.tableModel.setValueAt(date, selectedIndex, 1);
        view.tableModel.setValueAt(location, selectedIndex, 2);

        JOptionPane.showMessageDialog(view, "Event updated successfully.");
        clearFields();
    }

    // Delete Event
    private void deleteEvent() {
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(view, "Please select an event to delete.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                view,
                "Are you sure you want to delete this event?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            eventList.remove(selectedIndex);
            view.tableModel.removeRow(selectedIndex);

            JOptionPane.showMessageDialog(view, "Event deleted successfully.");
            clearFields();
        }
    }

    // Clear Fields
    private void clearFields() {
        view.txtName.setText("");
        view.txtDate.setText("");
        view.txtLocation.setText("");

        view.eventTable.clearSelection();
        selectedIndex = -1;
    }
}
