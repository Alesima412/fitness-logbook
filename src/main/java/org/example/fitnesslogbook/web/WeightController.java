package org.example.fitnesslogbook.web;

import org.example.fitnesslogbook.application.repository.UserRepository;
import org.example.fitnesslogbook.domain.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Controller
@RequestMapping("/weight")
public class WeightController {

    private final UserRepository userRepository;

    public WeightController(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
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
                    } catch (Exception e) {
                        // Skip invalid lines
                    }
                } else if (parts.length == 1) {
                    try {
                        double weight = Double.parseDouble(parts[0].trim());
                        user.recordWeight(weight);
                        count++;
                    } catch (Exception e) {
                        // Skip invalid lines
                    }
                }
            }
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success", "Successfully imported " + count + " measurements.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to process the CSV file.");
        }

        return "redirect:/dashboard";
    }
}
