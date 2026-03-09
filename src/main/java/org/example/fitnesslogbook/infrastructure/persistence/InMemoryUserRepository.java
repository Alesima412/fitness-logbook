package org.example.fitnesslogbook.infrastructure.persistence;

import org.example.fitnesslogbook.domain.model.User;
import org.example.fitnesslogbook.application.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private User appUser = null;

    @Override
    public boolean existsAnyUser() {
        return appUser != null;
    }

    @Override
    public Optional<User> getAppUser() {
        return Optional.ofNullable(appUser);
    }

    @Override
    public void save(User user) {
        this.appUser = user;
    }
}
