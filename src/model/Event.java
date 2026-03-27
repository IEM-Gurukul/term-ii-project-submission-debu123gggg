package model;

import java.time.LocalDate;

public class Event {
    private static int idCounter = 1;

    private int id;
    private String name;
    private String category;
    private LocalDate date;
    private String venue;
    private int capacity;
    private String description;
    private String status;

    public Event(String name, String category, LocalDate date, String venue, int capacity, String description) {
        this.id = idCounter++;
        this.name = name;
        this.category = category;
        this.date = date;
        this.venue = venue;
        this.capacity = capacity;
        this.description = description;
        this.status = "Upcoming";
    }

    // Getters
    public int getId()            { return id; }
    public String getName()       { return name; }
    public String getCategory()   { return category; }
    public LocalDate getDate()    { return date; }
    public String getVenue()      { return venue; }
    public int getCapacity()      { return capacity; }
    public String getDescription(){ return description; }
    public String getStatus()     { return status; }

    // Setters
    public void setName(String name)             { this.name = name; }
    public void setCategory(String category)     { this.category = category; }
    public void setDate(LocalDate date)          { this.date = date; }
    public void setVenue(String venue)           { this.venue = venue; }
    public void setCapacity(int capacity)        { this.capacity = capacity; }
    public void setDescription(String description){ this.description = description; }
    public void setStatus(String status)         { this.status = status; }

    @Override
    public String toString() {
        return String.format("Event{id=%d, name='%s', category='%s', date=%s, venue='%s', capacity=%d, status='%s'}",
                id, name, category, date, venue, capacity, status);
    }
}