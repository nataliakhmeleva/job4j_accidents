package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.data.UserDataRepository;

@Service
@AllArgsConstructor
public class UserDataService {
    private final UserDataRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }
}
