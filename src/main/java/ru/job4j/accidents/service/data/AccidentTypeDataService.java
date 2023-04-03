package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.data.AccidentTypeDataRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeDataService {
    private final AccidentTypeDataRepository accidentTypeRepository;

    public Optional<AccidentType> findByIdType(int id) {
        return accidentTypeRepository.findById(id);
    }

    public List<AccidentType> findAllTypes() {
        return accidentTypeRepository.findAll();
    }
}
