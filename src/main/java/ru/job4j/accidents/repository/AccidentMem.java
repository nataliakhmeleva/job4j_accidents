package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final AtomicInteger nextId = new AtomicInteger();
    private  final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        save(new Accident(0, "Авария1", "ДТП с участием двух легковых автомобилей.", "г. Москва, пр-т Мира"));
        save(new Accident(0, "Авария2", "ДТП с участием легкового и грузового автомобилей.", "г. Санкт-Петербург, Невский пр-т"));
        save(new Accident(0, "Авария3", "ДТП с участием легкового автомобиля и пешехода.", "г. Сочи, ул. Пушкина"));
    }

    public Accident save(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
