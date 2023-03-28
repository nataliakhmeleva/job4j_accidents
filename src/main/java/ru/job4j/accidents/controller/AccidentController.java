package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;

@Controller
@AllArgsConstructor
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentService.findAllTypes());
        model.addAttribute("rules", accidentService.findAllRules());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam("rIds") List<Integer> listIds) {
        var typeOptional = accidentService.findByIdType(accident.getType().getId());
        var ruleSet = accidentService.findByIdRule(listIds);
        accident.setType(typeOptional.get());
        accident.setRules(ruleSet);
        accidentService.save(accident);
        return "redirect:/";
    }

    @GetMapping("/editAccident")
    public String getById(@RequestParam("id") int id, Model model) {
        var accidentOptional = accidentService.findById(id);
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", accidentService.findAllTypes());
        model.addAttribute("rules", accidentService.findAllRules());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, @RequestParam("rIds") List<Integer> listIds) {
        var typeOptional = accidentService.findByIdType(accident.getType().getId());
        var ruleSet = accidentService.findByIdRule(listIds);
        accident.setType(typeOptional.get());
        accident.setRules(ruleSet);
        accidentService.update(accident);
        return "redirect:/";
    }
}