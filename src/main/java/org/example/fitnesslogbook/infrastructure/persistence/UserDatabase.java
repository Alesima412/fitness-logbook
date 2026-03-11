package org.example.fitnesslogbook.infrastructure.persistence;

import org.example.fitnesslogbook.application.repository.UserRepository;
import org.example.fitnesslogbook.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDatabase implements UserRepository {
    private final UserRepo userRepo;

    public UserDatabase(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean existsAnyUser() {
        return !userRepo.findAll().isEmpty();
    }

    @Override
    public Optional<User> getAppUser() {
        // Since this is a single-user logbook for now, we fetch the first user or handle accordingly
        return userRepo.findAll().stream().findFirst();
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

}
