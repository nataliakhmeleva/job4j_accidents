package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentHibernate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentHibernate accidentsRepostiory;

    public Accident save(Accident accident) {
        return accidentsRepostiory.save(accident);
    }

    public boolean update(Accident accident) {
        return accidentsRepostiory.update(accident);
    }

    public Optional<Accident> findById(int id) {
        return accidentsRepostiory.findById(id);
    }

    public List<Accident> findAll() {
        return accidentsRepostiory.findAll();
    }

    public Optional<AccidentType> findByIdType(int id) {
        return accidentsRepostiory.findByIdType(id);
    }

    public List<AccidentType> findAllTypes() {
        return accidentsRepostiory.findAllTypes();
    }

    public Set<Rule> findByIdRule(List<Integer> listId) {
        return accidentsRepostiory.findByIdRule(listId);
    }

    public List<Rule> findAllRules() {
        return accidentsRepostiory.findAllRules();
    }
}
