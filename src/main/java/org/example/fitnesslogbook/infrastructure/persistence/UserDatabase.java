package org.example.fitnesslogbook.infrastructure.persistence;

import org.example.fitnesslogbook.application.repository.UserRepository;

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
    public java.util.Optional<org.example.fitnesslogbook.domain.model.User> getAppUser() {
        // Since this is a single-user logbook for now, we fetch the first user or handle accordingly
        return userRepo.findAll().iterator().hasNext()
                ? java.util.Optional.of(userRepo.findAll().iterator().next())
                : java.util.Optional.empty();
    }

    @Override
    public void save(org.example.fitnesslogbook.domain.model.User user) {
        userRepo.save(user);
    }

}
