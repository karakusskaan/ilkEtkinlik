package eg.itis.services;

import eg.itis.models.Event;
import eg.itis.models.User;
import eg.itis.repositories.EventsRepository;
import eg.itis.repositories.UsersRepository;

import java.time.LocalDate;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

public class AppService {

    private final UsersRepository usersRepository;
    private final EventsRepository eventsRepository;

    public AppService(UsersRepository usersRepository, EventsRepository eventsRepository) {
        this.usersRepository = usersRepository;
        this.eventsRepository = eventsRepository;
    }

    public void signUp(String email, String password) {
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(email)
                .password(password)
                .build();

        usersRepository.save(user);
    }

    public void addEvent(String name, LocalDate date) {
        Event event = Event.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .date(date)
                .build();

        eventsRepository.save(event);
    }

    public void addUserToEvent(String emailUser, String nameEvent) {
        User user = usersRepository.findByEmail(emailUser);

        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        Event event = eventsRepository.findByName(nameEvent);

        if (event == null) {
            throw new IllegalArgumentException("Событие не найдено");
        }

        eventsRepository.saveUserToEvent(user, event);
    }

    public List<Event> getAllEventsByUser(String email) {
        User user = usersRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("Пользователь не найден");
        }

        List<Event> events = new ArrayList<>();

        // Get all events in which the user participates
        List<Event> eventsWithUser = eventsRepository.findAllByMembersContains(user);

        events.addAll(eventsWithUser);

        return events;
    }
}
