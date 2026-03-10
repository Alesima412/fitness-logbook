package org.example.fitnesslogbook.web;

import org.example.fitnesslogbook.application.repository.UserRepository;
import org.example.fitnesslogbook.domain.model.Exercise;
import org.example.fitnesslogbook.domain.model.ExerciseSet;
import org.example.fitnesslogbook.domain.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exercise")
public class ExerciseController {

    private final UserRepository userRepository;

    public ExerciseController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/add")
    public String addExercise(@RequestParam("name") String name, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) return "redirect:/start";
        
        User user = userOptional.get();
        try {
            user.addExercise(name);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Exercise added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add exercise.");
        }
        return "redirect:/dashboard";
    }

    @GetMapping("/{name}")
    public String exerciseDashboard(@PathVariable("name") String name, Model model) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) return "redirect:/start";
        
        User user = userOptional.get();
        Exercise exercise = user.getExercise(name);
        
        if (exercise == null) {
            return "redirect:/dashboard";
        }
        
        model.addAttribute("exercise", exercise);
        
        // Prepare chart data (Total volume per date or max weight per date. Let's do max weight for simplicity)
        List<ExerciseSet> sets = exercise.getExerciseSets();
        List<String> labels = sets.stream()
                .map(s -> s.getDate().toString())
                .distinct()
                .collect(Collectors.toList());
                
        // For each date, find max weight
        List<Double> data = labels.stream().map(dateStr -> {
            return sets.stream()
                .filter(s -> s.getDate().toString().equals(dateStr))
                .mapToDouble(ExerciseSet::getWeight)
                .max()
                .orElse(0.0);
        }).collect(Collectors.toList());

        model.addAttribute("chartLabels", labels);
        model.addAttribute("chartData", data);
        
        return "exercise-dashboard";
    }

    @PostMapping("/{name}/addSet")
    public String addSet(@PathVariable("name") String name,
                         @RequestParam("reps") int reps,
                         @RequestParam("weight") double weight,
                         RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) return "redirect:/start";
        
        User user = userOptional.get();
        try {
            user.logExerciseSet(name, reps, weight);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Set added successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add set.");
        }
        return "redirect:/exercise/" + name;
    }
}
