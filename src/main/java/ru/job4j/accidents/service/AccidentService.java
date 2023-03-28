package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.Collection;
import java.util.Optional;

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
}
