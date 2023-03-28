package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final AtomicInteger nextId = new AtomicInteger();
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>(Map.of(
            1, new AccidentType(1, "Две машины"),
            2, new AccidentType(2, "Машина и человек"),
            3, new AccidentType(3, "Машина и велосипед")
    ));

    public AccidentMem() {
        save(new Accident(0, "Авария1", "ДТП с участием двух легковых автомобилей.", "г. Москва, пр-т Мира", types.get(1)));
        save(new Accident(0, "Авария2", "ДТП с участием легкового и грузового автомобилей.", "г. Санкт-Петербург, Невский пр-т", types.get(1)));
        save(new Accident(0, "Авария3", "ДТП с участием легкового автомобиля и пешехода.", "г. Сочи, ул. Пушкина", types.get(2)));
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
}
