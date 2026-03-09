package org.example.fitnesslogbook.application.repository;

import org.example.fitnesslogbook.domain.model.User;
import java.util.Optional;

public interface UserRepository {
    boolean existsAnyUser();
    Optional<User> getAppUser();
    void save(User user);
}
