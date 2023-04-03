package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.repository.data.AuthorityDataRepository;

@Service
@AllArgsConstructor
public class AuthorityDataService {
    private final AuthorityDataRepository authorityRepository;

    public Authority findByAuthority(String authority) {
        return authorityRepository.findByAuthority(authority);
    }
}
