package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private final JdbcTemplate jdbc;
    private final RowMapper<Accident> accidentRowMapper = (rs, row) -> {
        Accident accident = new Accident();
        accident.setId(rs.getInt("id"));
        accident.setName(rs.getString("name"));
        accident.setText(rs.getString("text"));
        accident.setAddress(rs.getString("address"));
        accident.setType(findByIdType(rs.getInt("type_id")).get());
        accident.setRules(findRulesByAccidentId(rs.getInt("id")));
        return accident;
    };

    private final RowMapper<AccidentType> typeRowMapper = (rs, row) -> {
        AccidentType type = new AccidentType();
        type.setId(rs.getInt("id"));
        type.setName(rs.getString("name"));
        return type;
    };

    private final RowMapper<Rule> ruleRowMapper = (rs, row) -> {
        Rule rule = new Rule();
        rule.setId(rs.getInt("id"));
        rule.setName(rs.getString("name"));
        return rule;
    };

    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into accidents (name, text, address, type_id) values (?, ?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        accident.setId((Integer) keyHolder.getKey());
        insertAccidentRules(accident);
        return accident;
    }

    public boolean update(Accident accident) {
        var isUpdated = jdbc.update("update accidents set name = ?, text = ?, address = ?, type_id = ? where id = ?",
                accident.getName(), accident.getText(), accident.getAddress(), accident.getType().getId(), accident.getId());
        insertAccidentRules(accident);
        return isUpdated > 0;
    }

    public List<Accident> findAll() {
        return jdbc.query("select id, name, text, address, type_id from accidents ORDER BY id",
                accidentRowMapper);
    }

    public Optional<Accident> findById(int id) {
        var accidentOptional = jdbc.queryForObject("select id, name, text, address, type_id from accidents where id = ?",
                accidentRowMapper, id);
        return Optional.ofNullable(accidentOptional);
    }

    public Optional<AccidentType> findByIdType(int id) {
        var typeOptional = jdbc.queryForObject("select id, name from accident_types where id = ?",
                typeRowMapper, id);
        return Optional.ofNullable(typeOptional);
    }

    public List<AccidentType> findAllTypes() {
        return jdbc.query("select id, name from accident_types",
                typeRowMapper);
    }

    public Set<Rule> findByIdRule(List<Integer> listId) {
        Set<Rule> set = new HashSet<>();
        for (Integer idRule : listId) {
            var rule = jdbc.queryForObject("select id, name from rules where id = ?",
                    ruleRowMapper, idRule);
            set.add(rule);
        }
        return set;
    }

    public List<Rule> findAllRules() {
        return jdbc.query("select id, name from rules",
                ruleRowMapper);
    }

    private void insertAccidentRules(Accident accident) {
        for (Rule rule : accident.getRules()) {
            jdbc.update("insert into accident_rules(accident_id, rule_id) values (?, ?)",
                    accident.getId(), rule.getId());
        }
    }

    private Set<Rule> findRulesByAccidentId(int accidentId) {
        var rules = jdbc.query("select rule_id from accident_rules where accident_id = ?",
                (rs, row) -> rs.getInt("rule_id"), accidentId);

        return findByIdRule(rules);
    }
}
