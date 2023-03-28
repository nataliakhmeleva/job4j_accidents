package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class AccidentMem {
    private final AtomicInteger nextId = new AtomicInteger();
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>(Map.of(
            1, new AccidentType(1, "Две машины"),
            2, new AccidentType(2, "Машина и человек"),
            3, new AccidentType(3, "Машина и велосипед")
    ));

    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>(Map.of(
            1, new Rule(1, "Статья. 1"),
            2, new Rule(2, "Статья. 2"),
            3, new Rule(3, "Статья. 3")
    ));


    public AccidentMem() {
        save(new Accident(0, "Авария1", "ДТП с участием двух легковых автомобилей.", "г. Москва, пр-т Мира", types.get(1), Set.of(rules.get(1))));
        save(new Accident(0, "Авария2", "ДТП с участием легкового и грузового автомобилей.", "г. Санкт-Петербург, Невский пр-т", types.get(1), Set.of(rules.get(1))));
        save(new Accident(0, "Авария3", "ДТП с участием легкового автомобиля и пешехода.", "г. Сочи, ул. Пушкина", types.get(2), Set.of(rules.get(2), rules.get(3))));
    }

    public Accident save(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    public boolean update(Accident accident) {
        return accidents.replace(accident.getId(), accident) != null;
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }

    public Optional<AccidentType> findByIdType(int id) {
        return Optional.ofNullable(types.get(id));
    }

    public Collection<AccidentType> findAllTypes() {
        return types.values();
    }

    public Set<Rule> findByIdRule(List<Integer> listId) {
        return rules.values().stream().filter(o -> listId.contains(o.getId())).collect(Collectors.toSet());

    }

    public Collection<Rule> findAllRules() {
        return rules.values();
    }
}
