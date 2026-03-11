package org.example.fitnesslogbook.infrastructure.persistence;

import org.example.fitnesslogbook.domain.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<User, String> {
    @Override
    List<User> findAll();
}
