package org.example.fitnesslogbook.web;

import jakarta.validation.Valid;
import org.example.fitnesslogbook.domain.model.User;
import org.example.fitnesslogbook.application.repository.UserRepository;
import org.example.fitnesslogbook.web.dto.OnboardingForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/onboarding")
public class OnboardingController {

    private final UserRepository userRepository;

    public OnboardingController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String onboardingForm(org.springframework.ui.Model model) {
        if (userRepository.existsAnyUser()) {
            return "redirect:/dashboard";
        }
        model.addAttribute("onboardingForm", new OnboardingForm());
        return "onboarding";
    }

    @PostMapping
    public String finishOnboarding(@Valid @ModelAttribute("onboardingForm") OnboardingForm form, BindingResult result) {
        if(result.hasErrors()){
            return "onboarding";
        }
        if (userRepository.existsAnyUser()) {
            return "redirect:/dashboard";
        }
        
        User user = User.create(form.getName(), form.getAge(), form.getHeight());
        user.recordWeight(form.getWeight());
        userRepository.save(user);
        
        return "redirect:/dashboard";
    }
}
