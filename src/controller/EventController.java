package controller;

import model.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventController {

    private List<Event> eventList = new ArrayList<>();

    // ─── CREATE ──────────────────────────────────────────────────────────
    public void addEvent(String name, String category, LocalDate date,
                         String venue, int capacity, String description) {
        Event event = new Event(name, category, date, venue, capacity, description);
        eventList.add(event);
    }

    // ─── READ ─────────────────────────────────────────────────────────────
    public List<Event> getAllEvents() {
        return new ArrayList<>(eventList);
    }

    public Event getEventById(int id) {
        return eventList.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<Event> searchEvents(String keyword) {
        String kw = keyword.toLowerCase();
        return eventList.stream()
                .filter(e -> e.getName().toLowerCase().contains(kw)
                          || e.getCategory().toLowerCase().contains(kw)
                          || e.getVenue().toLowerCase().contains(kw)
                          || e.getStatus().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────
    public boolean updateEvent(int id, String name, String category, LocalDate date,
                               String venue, int capacity, String description, String status) {
        Event event = getEventById(id);
        if (event != null) {
            event.setName(name);
            event.setCategory(category);
            event.setDate(date);
            event.setVenue(venue);
            event.setCapacity(capacity);
            event.setDescription(description);
            event.setStatus(status);
            return true;
        }
        return false;
    }

    // ─── DELETE ───────────────────────────────────────────────────────────
    public boolean deleteEvent(int id) {
        return eventList.removeIf(e -> e.getId() == id);
    }

    // ─── STATS ────────────────────────────────────────────────────────────
    public int getTotalEvents()     { return eventList.size(); }

    public long getUpcomingCount() {
        return eventList.stream().filter(e -> e.getStatus().equals("Upcoming")).count();
    }

    public long getCompletedCount() {
        return eventList.stream().filter(e -> e.getStatus().equals("Completed")).count();
    }

    public long getCancelledCount() {
        return eventList.stream().filter(e -> e.getStatus().equals("Cancelled")).count();
    }

    // ─── SEED DATA ────────────────────────────────────────────────────────
    public void loadSampleData() {
        addEvent("Tech Conference 2025",   "Conference",  LocalDate.of(2025, 9, 15),  "Convention Center",   500, "Annual tech summit");
        addEvent("Jazz Night",             "Music",       LocalDate.of(2025, 8, 20),  "City Auditorium",     200, "Live jazz performances");
        addEvent("Art Exhibition",         "Exhibition",  LocalDate.of(2025, 10, 5),  "Art Gallery",         150, "Modern art showcase");
        addEvent("Startup Meetup",         "Networking",  LocalDate.of(2025, 7, 30),  "Innovation Hub",      100, "Entrepreneur networking");
        addEvent("Food Festival",          "Festival",    LocalDate.of(2025, 11, 12), "Central Park",       1000, "Cuisine from 50+ vendors");
    }
}