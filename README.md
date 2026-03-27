[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/pG3gvzt-)
# PCCCS495 – Term II Project

# Event Management System using MVC Architecture (Java Swing GUI)

## Project Title

**Event Management System using MVC Architecture (Java Swing GUI)**

---

## Problem Statement (max 150 words)

Managing event details manually can be difficult and time-consuming, especially when multiple events need to be tracked. This project solves that problem by creating a simple desktop-based Event Management System using Java and Swing. The system allows users to add, view, update, and delete event records through a graphical user interface. It follows the MVC (Model-View-Controller) architecture to separate data, user interface, and business logic, making the project easy to understand and maintain. This project is suitable for beginners to learn Java GUI development, object-oriented programming concepts, and basic CRUD operations in a structured way.

---

## Target User

- College students learning Java and OOP concepts
- Beginners practicing Java Swing GUI
- Teachers evaluating MVC-based mini projects
- Small users who want to manage event records in a simple desktop application

---

## Core Features

- Add a new event with name, date, and location
- View all events in a table format
- Update selected event details
- Delete selected event record
- Clear input fields after operations
- Simple and user-friendly Java Swing GUI
- MVC architecture for better code organization

---

## OOP Concepts Used

- **Abstraction:**  
  The `Event` class abstracts event data such as event name, date, and location.

- **Inheritance:**  
  The `EventView` class extends `JFrame`, using inheritance from Swing GUI components.

- **Polymorphism:**  
  Method overriding and event handling through action listeners in Swing demonstrate runtime behavior.

- **Exception Handling:**  
  Input validation and user messages are handled using dialog boxes (`JOptionPane`) to prevent invalid operations.

- **Collections / Threads:**  
  `ArrayList<Event>` is used to store event records dynamically.  
  `SwingUtilities.invokeLater()` is used to safely launch the GUI on the Event Dispatch Thread.

---

## Proposed Architecture Description

This project follows the **MVC (Model-View-Controller)** architecture:

- **Model (`Event.java`)**  
  Stores event data such as event name, date, and location.

- **View (`EventView.java`)**  
  Builds the Java Swing graphical user interface including text fields, buttons, and table.

- **Controller (`EventController.java`)**  
  Handles user actions like Add, Update, Delete, and Clear. It connects the View with the Model and updates data accordingly.

- **Main (`Main.java`)**  
  Starts the application and launches the GUI.

This separation makes the code more modular, readable, and maintainable.

---

## File Structure

```bash
EventManagementSystem/
│
├── src/
│   ├── model/
│   │   └── Event.java
│   ├── view/
│   │   └── EventView.java
│   ├── controller/
│   │   └── EventController.java
│   └── Main.java
│
└── out/   (generated after compilation)
