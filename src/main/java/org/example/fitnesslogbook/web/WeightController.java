package org.example.fitnesslogbook.web;

import org.example.fitnesslogbook.application.repository.UserRepository;
import org.example.fitnesslogbook.domain.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/weight")
public class WeightController {

    private final UserRepository userRepository;

    public WeightController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/manage")
    public String manageWeights(Model model) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) {
            return "redirect:/start";
        }
        model.addAttribute("measurements", userOptional.get().getMeasurements());
        return "manage-weights";
    }

    @PostMapping("/update/{id}")
    public String updateWeight(@PathVariable("id") UUID id,
                               @RequestParam("weight") double weight,
                               @RequestParam("date") String dateStr,
                               RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) return "redirect:/start";
        
        User user = userOptional.get();
        try {
            LocalDate date = LocalDate.parse(dateStr);
            user.updateWeight(id, weight, date);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Measurement updated successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update measurement.");
        }
        return "redirect:/weight/manage";
    }

    @PostMapping("/delete/{id}")
    public String deleteWeight(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) return "redirect:/start";
        
        User user = userOptional.get();
        user.deleteWeight(id);
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("success", "Measurement deleted successfully.");
        return "redirect:/weight/manage";
    }

    @PostMapping("/add")
    public String addWeight(@RequestParam("weight") double weight, 
                            @RequestParam(value = "date", required = false) String dateStr,
                            RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) {
            return "redirect:/start";
        }
        User user = userOptional.get();

        try {
            LocalDate date = (dateStr != null && !dateStr.trim().isEmpty()) ? LocalDate.parse(dateStr) : LocalDate.now();
            user.recordWeight(weight, date);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Weight added successfully.");
        } catch (IllegalArgumentException | DateTimeParseException e) {
            redirectAttributes.addFlashAttribute("error", "Invalid weight or date format.");
        }
        
        return "redirect:/dashboard";
    }

    @PostMapping("/upload")
    public String uploadCsv(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.getAppUser();
        if (userOptional.isEmpty()) {
            return "redirect:/start";
        }
        User user = userOptional.get();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a file to upload.");
            return "redirect:/dashboard";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // Skip header or empty lines
                if (line.isEmpty() || line.toLowerCase().startsWith("date")) continue; 
                
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    try {
                        LocalDate date = LocalDate.parse(parts[0].trim());
                        double weight = Double.parseDouble(parts[1].trim());
                        user.recordWeight(weight, date);
                        count++;
                    } catch (NullPointerException | DateTimeParseException | NumberFormatException e) {
                        // Skip invalid lines
                    }
                } else if (parts.length == 1) {
                    try {
                        double weight = Double.parseDouble(parts[0].trim());
                        user.recordWeight(weight);
                        count++;
                    } catch (NullPointerException | NumberFormatException e) {
                        // Skip invalid lines
                    }
                }
            }
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Successfully imported " + count + " measurements.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Failed to process the CSV file.");
        }

        return "redirect:/dashboard";
    }
}
