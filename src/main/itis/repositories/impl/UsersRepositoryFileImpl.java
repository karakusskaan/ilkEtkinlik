package eg.itis.repositories.impl;

import eg.itis.models.User;
import eg.itis.repositories.UsersRepository;

import java.io.*;

public class UsersRepositoryFileImpl implements UsersRepository {

    private final String fileName;

    public UsersRepositoryFileImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(User model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))){
            writer.write(model.getId() + "|" + model.getEmail() + "|" + model.getPassword());
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public User findByEmail(String emailUser) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String id = parts[0];
                String email = parts[1];
                String password = parts[2];

                if (email.equals(emailUser)) {
                    return User.builder()
                            .id(id)
                            .email(email)
                            .password(password)
                            .build();
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return null; // User with the specified email not found
    }

}
