package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    private final CrudRepository crudRepository;

    public Accident save(Accident accident) {
        crudRepository.run(session -> session.persist(accident));
        return accident;
    }

    public boolean update(Accident accident) {
        try {
            crudRepository.run(session -> session.merge(accident));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Accident> findAll() {
        return crudRepository.query("SELECT DISTINCT f FROM Accident f JOIN FETCH f.type "
                + "JOIN FETCH f.rules ORDER BY f.id", Accident.class);
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional("FROM Accident f JOIN FETCH f.type "
                        + "JOIN FETCH f.rules where f.id = :fId", Accident.class,
                Map.of("fId", id));
    }

    public List<AccidentType> findAllTypes() {
        return crudRepository.query("FROM AccidentType ORDER BY id", AccidentType.class);
    }

    public Optional<AccidentType> findByIdType(int id) {
        return crudRepository.optional("FROM AccidentType where id = :fId", AccidentType.class,
                Map.of("fId", id));
    }

    public List<Rule> findAllRules() {
        return crudRepository.query("FROM Rule ORDER BY id", Rule.class);
    }

    public Set<Rule> findByIdRule(List<Integer> listId) {
        var list = crudRepository.query("FROM Rule where id in :fId", Rule.class,
                Map.of("fId", listId));
        return new HashSet<>(list);
    }
}
