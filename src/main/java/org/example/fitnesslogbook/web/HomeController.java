package org.example.fitnesslogbook.web;

import org.example.fitnesslogbook.application.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index() {
        if (userRepository.existsAnyUser()) {
            return "redirect:/dashboard";
        } else {
            return "index";
        }
    }

    @GetMapping("/start")
    public String start() {
            return "redirect:/onboarding";
    }
}
