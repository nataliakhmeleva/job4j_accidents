package ru.job4j.accidents.repository.data;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.User;


public interface UserDataRepository extends CrudRepository<User, Integer> {
    User save(User user);
}
