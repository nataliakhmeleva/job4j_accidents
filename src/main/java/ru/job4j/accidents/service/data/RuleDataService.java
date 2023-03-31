package ru.job4j.accidents.service.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.data.RuleDataRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RuleDataService {
    private final RuleDataRepository ruleRepository;

    public Set<Rule> findByIdRule(List<Integer> listId) {
        Set<Rule> rules = new HashSet<>();
        for (Rule rule : ruleRepository
                .findAllById(listId)) {
            rules.add(rule);
        }
        return rules;
    }

    public List<Rule> findAllRules() {
        return (List<Rule>) ruleRepository.findAll();
    }
}
