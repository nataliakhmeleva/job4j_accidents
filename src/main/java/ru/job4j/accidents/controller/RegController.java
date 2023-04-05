package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(RegController.class.getName());
    private final PasswordEncoder encoder;
    private final UserDataService users;
    private final AuthorityDataService authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            users.save(user);
            return "redirect:/login";
        } catch (Exception e) {
            LOGGER.error("errorMessage", e);
        }
        model.addAttribute("errorMessage", "The user with login already exists.");
        return "reg";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}
