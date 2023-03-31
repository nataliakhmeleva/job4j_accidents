package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.data.AccidentDataRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentDataService {
    private final AccidentDataRepository accidentsRepository;

    public Accident save(Accident accident) {
        return accidentsRepository.save(accident);
    }

    public boolean update(Accident accident) {
        if (accidentsRepository.existsById(accident.getId())) {
            accidentsRepository.save(accident);
            return true;
        }
        return false;
    }

    public Optional<Accident> findById(int id) {
        return accidentsRepository.findById(id);
    }

    public List<Accident> findAll() {
        return (List<Accident>) accidentsRepository.findAll();
    }
}
