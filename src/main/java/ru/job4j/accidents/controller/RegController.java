package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.data.AuthorityDataService;
import ru.job4j.accidents.service.data.UserDataService;

@Controller
@AllArgsConstructor
public class RegController {
    private final PasswordEncoder encoder;
    private final UserDataService users;
    private final AuthorityDataService authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        String errorMessage;
        try {
            users.save(user);
            return "redirect:/login";
        } catch (Exception e) {
            errorMessage = "The user with login already exists.";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "reg";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}
