package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;

    public Accident save(Accident accident) {
        return accidentMem.save(accident);
    }

    public boolean update(Accident accident) {
        return accidentMem.update(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentMem.findById(id);
    }

    public Collection<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Optional<AccidentType> findByIdType(int id) {
        return accidentMem.findByIdType(id);
    }

    public Collection<AccidentType> findAllTypes() {
        return accidentMem.findAllTypes();
    }

    public Set<Rule> findByIdRule(List<Integer> listId) {
        return accidentMem.findByIdRule(listId);
    }

    public Collection<Rule> findAllRules() {
        return accidentMem.findAllRules();
    }
}
