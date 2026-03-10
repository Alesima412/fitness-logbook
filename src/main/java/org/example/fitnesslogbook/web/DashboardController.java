package org.example.fitnesslogbook.web;

import org.example.fitnesslogbook.application.repository.UserRepository;
import org.example.fitnesslogbook.domain.model.Exercise;
import org.example.fitnesslogbook.domain.model.ExerciseSet;
import org.example.fitnesslogbook.domain.model.User;
import org.example.fitnesslogbook.domain.model.WeightMeasurements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String dashboard(Model model) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) {
            return "redirect:/start";
        }
        
        User user = userOptional.get();
        model.addAttribute("user", user);
        
        // Prepare data for the weight chart
        List<WeightMeasurements> measurements = user.getMeasurements();
        List<String> labels = measurements.stream()
                .map(m -> m.getDate().toString())
                .collect(Collectors.toList());
        List<Double> data = measurements.stream()
                .map(WeightMeasurements::getWeight)
                .collect(Collectors.toList());
                
        model.addAttribute("chartLabels", labels);
        model.addAttribute("chartData", data);

        // Prepare data for pinned exercise chart
        Exercise pinned = user.getPinnedExercise();
        if (pinned != null) {
            model.addAttribute("pinnedExerciseName", pinned.getName());
            List<ExerciseSet> sets = pinned.getExerciseSets();
            List<String> pinnedLabels = sets.stream()
                    .map(s -> s.getDate().toString())
                    .distinct()
                    .collect(Collectors.toList());
            List<Double> pinnedData = pinnedLabels.stream().map(dateStr -> {
                return sets.stream()
                    .filter(s -> s.getDate().toString().equals(dateStr))
                    .mapToDouble(ExerciseSet::getWeight)
                    .max()
                    .orElse(0.0);
            }).collect(Collectors.toList());
            model.addAttribute("pinnedChartLabels", pinnedLabels);
            model.addAttribute("pinnedChartData", pinnedData);
        }

        return "dashboard";
    }
}
