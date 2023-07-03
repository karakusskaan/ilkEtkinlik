
package eg.itis;

import eg.itis.models.Event;
import eg.itis.models.User;
import eg.itis.repositories.EventsRepository;
import eg.itis.repositories.UsersRepository;
import eg.itis.repositories.impl.EventsRepositoryFileImpl;
import eg.itis.repositories.impl.UsersRepositoryFileImpl;
import eg.itis.services.AppService;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create repositories
        UsersRepository usersRepository = new UsersRepositoryFileImpl("users.txt");
        EventsRepository eventsRepository = new EventsRepositoryFileImpl("events.txt", "events_users.txt");

        // Create service
        AppService appService = new AppService(usersRepository, eventsRepository);

        // Sign up 5 users
        appService.signUp("john.doe@example.com", "password123");
        appService.signUp("alice.smith@example.com", "password456");
        appService.signUp("mike.johnson@example.com", "password789");
        appService.signUp("emma.wilson@example.com", "passwordabc");
        appService.signUp("alexander.brown@example.com", "passwordxyz");

        // Add 10 events
        appService.addEvent("Birthday Party", LocalDate.now());
        appService.addEvent("Conference", LocalDate.now().plusDays(1));
        appService.addEvent("Music Festival", LocalDate.now().plusDays(2));
        appService.addEvent("Wedding Ceremony", LocalDate.now().plusDays(3));
        appService.addEvent("Sports Tournament", LocalDate.now().plusDays(4));
        appService.addEvent("Art Exhibition", LocalDate.now().plusDays(5));
        appService.addEvent("Movie Night", LocalDate.now().plusDays(6));
        appService.addEvent("Charity Event", LocalDate.now().plusDays(7));
        appService.addEvent("Business Networking", LocalDate.now().plusDays(8));
        appService.addEvent("Holiday Celebration", LocalDate.now().plusDays(9));

        // Add users to events
        appService.addUserToEvent("john.doe@example.com", "Birthday Party");
        appService.addUserToEvent("john.doe@example.com", "Conference");
        appService.addUserToEvent("alice.smith@example.com", "Birthday Party");
        appService.addUserToEvent("alice.smith@example.com", "Music Festival");
        appService.addUserToEvent("mike.johnson@example.com", "Conference");
        appService.addUserToEvent("mike.johnson@example.com", "Wedding Ceremony");
        appService.addUserToEvent("emma.wilson@example.com", "Music Festival");
        appService.addUserToEvent("emma.wilson@example.com", "Sports Tournament");
        appService.addUserToEvent("alexander.brown@example.com", "Wedding Ceremony");
        appService.addUserToEvent("alexander.brown@example.com", "Sports Tournament");

        // Get all events for a user
        List<Event> events = appService.getAllEventsByUser("john.doe@example.com");

        // Print the events
        System.out.println("Events for john.doe@example.com:");
        for (Event event : events) {
            System.out.println(event);
        }

        // Find event by name
        Event foundEvent = eventsRepository.findByName("Conference");
        if (foundEvent != null) {
            System.out.println("Found event: " + foundEvent);
        } else {
            System.out.println("Event with the name 'Conference' not found.");
        }

        // Find user by email
        User foundUser = usersRepository.findByEmail("alice.smith@example.com");
        if (foundUser != null) {
            System.out.println("Found user: " + foundUser);
        } else {
            System.out.println("User with the email 'alice.smith@example.com' not found.");
        }
    }
}
