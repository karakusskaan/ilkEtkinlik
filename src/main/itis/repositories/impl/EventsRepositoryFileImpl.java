package eg.itis.repositories.impl;

import eg.itis.models.Event;
import eg.itis.models.User;
import eg.itis.repositories.EventsRepository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventsRepositoryFileImpl implements EventsRepository {
    private final String eventFileName;
    private final String eventsAndUsersFileName;

    public EventsRepositoryFileImpl(String eventFileName, String eventsAndUsersFileName) {
        this.eventFileName = eventFileName;
        this.eventsAndUsersFileName = eventsAndUsersFileName;
    }

    @Override
    public void save(Event model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(eventFileName, true))){
            writer.write(model.getId() + "|" + model.getName() + "|" + model.getDate());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Event findByName(String nameEvent) {
        try (BufferedReader reader = new BufferedReader(new FileReader(eventFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String id = parts[0];
                String name = parts[1];
                LocalDate date = LocalDate.parse(parts[2]);

                if (name.equals(nameEvent)) {
                    return Event.builder()
                            .id(id)
                            .name(name)
                            .date(date)
                            .build();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return null; // Event with the specified name not found
    }


    @Override
    public void saveUserToEvent(User user, Event event) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(eventsAndUsersFileName, true))){
            writer.write(user.getId() + "|" + event.getId());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Event> findAllByMembersContains(User user) {
        List<Event> events = new ArrayList<>();
        String userId = user.getId();

        try (BufferedReader reader = new BufferedReader(new FileReader(eventsAndUsersFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String userIdFromFile = parts[0];
                String eventId = parts[1];

                if (userIdFromFile.equals(userId)) {
                    Event event = findEventById(eventId);
                    if (event != null) {
                        events.add(event);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return events;
    }


    private Event findEventById(String eventId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(eventFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String id = parts[0];
                String name = parts[1];
                LocalDate date = LocalDate.parse(parts[2]);

                if (id.equals(eventId)) {
                    return Event.builder()
                            .id(id)
                            .name(name)
                            .date(date)
                            .build();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return null;
    }

}
