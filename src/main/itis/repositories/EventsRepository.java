package eg.itis.repositories;

import eg.itis.models.Event;
import eg.itis.models.User;

import java.util.List;

public interface EventsRepository extends CrudRepository<Event> {
    Event findByName(String nameEvent);

    void saveUserToEvent(User user, Event event);

    List<Event> findAllByMembersContains(User user);
}
