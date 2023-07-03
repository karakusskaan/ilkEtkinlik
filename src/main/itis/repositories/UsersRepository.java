package eg.itis.repositories;

import eg.itis.models.User;

public interface UsersRepository extends CrudRepository<User> {
    User findByEmail(String emailUser);
}
