package org.example.fitnesslogbook.infrastructure.persistence;

import org.example.fitnesslogbook.domain.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends CrudRepository<User, String> {
    @Override
    List<User> findAll();
}
