package org.example.fitnesslogbook.web;

import org.example.fitnesslogbook.application.repository.UserRepository;
import org.example.fitnesslogbook.domain.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/pin-exercise")
    public String pinExercise(@RequestParam("exerciseName") String exerciseName, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) return "redirect:/start";
        
        User user = userOptional.get();
        try {
            user.pinExercise(exerciseName);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", exerciseName + " pinned to dashboard.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to pin exercise.");
        }
        return "redirect:/dashboard";
    }
}
