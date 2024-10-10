package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.LawyerDAO; // Ensure you have a corresponding LawyerDAO
import com.example.demo.model.Lawyer; // Ensure you have a corresponding Lawyer model

@Controller
public class LawyerController {

    @Autowired
    private LawyerDAO lawyerDAO;

    @GetMapping("/register/lawyer")
    public String showLawyerForm(Model model) {
        Lawyer lawyer = new Lawyer();
        model.addAttribute("lawyer", lawyer);
        return "lawyer"; // Return the lawyer registration form view
    }

    @PostMapping("/register/lawyer")
    public String submitLawyerForm(@ModelAttribute("lawyer") Lawyer lawyer,
                                   @RequestParam String phoneNumber1,
                                   @RequestParam(required = false) String phoneNumber2,
                                   @RequestParam String email1,
                                   @RequestParam(required = false) String email2) {
        // Save the lawyer first to get the ID
        lawyerDAO.saveLawyer(lawyer);

        // After saving, retrieve the generated ID
        Integer lawyerId = lawyerDAO.getLastInsertId();

        // Save phone numbers if provided
        if (phoneNumber1 != null && !phoneNumber1.isEmpty()) {
            lawyerDAO.saveLawyerPhone(lawyerId, phoneNumber1);
        }
        if (phoneNumber2 != null && !phoneNumber2.isEmpty()) {
            lawyerDAO.saveLawyerPhone(lawyerId, phoneNumber2);
        }

        // Save emails if provided
        if (email1 != null && !email1.isEmpty()) {
            lawyerDAO.saveLawyerEmail(lawyerId, email1);
        }
        if (email2 != null && !email2.isEmpty()) {
            lawyerDAO.saveLawyerEmail(lawyerId, email2);
        }

        return "redirect:/lawyers"; // Redirect to success page
    }

    @GetMapping("/lawyers")
    public String listLawyers(Model model) {
        List<Lawyer> lawyers = lawyerDAO.listLawyers();
        model.addAttribute("lawyers", lawyers);
        return "law_list"; // Return a view name for displaying the lawyer list
    }

    @GetMapping("/lawyers/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        try {
            // Fetch the lawyer by ID
            Lawyer lawyer = lawyerDAO.getLawyerById(id);
            model.addAttribute("lawyer", lawyer);

            // Fetch associated phone numbers and emails
            List<String> lawyerPhones = lawyerDAO.getLawyerPhones(id); // List of phone numbers as Strings
            List<String> lawyerEmails = lawyerDAO.getLawyerEmails(id); // List of LawyerEmail objects
            
            model.addAttribute("lawyerPhones", lawyerPhones);
            model.addAttribute("lawyerEmails", lawyerEmails);

            // Safely retrieve phone numbers and emails
            String phoneNumber1 = lawyerPhones.size() > 0 ? lawyerPhones.get(0) : ""; // Correctly get the phone number
            String phoneNumber2 = lawyerPhones.size() > 1 ? lawyerPhones.get(1) : ""; // Correctly get the second phone number
            String email1 = lawyerEmails.size() > 0 ? lawyerEmails.get(0) : ""; // Correctly get the first email
            String email2 = lawyerEmails.size() > 1 ? lawyerEmails.get(1) : ""; // Correctly get the second email

            // Add the phone numbers and emails to the model
            model.addAttribute("phoneNumber1", phoneNumber1);
            model.addAttribute("phoneNumber2", phoneNumber2);
            model.addAttribute("email1", email1);
            model.addAttribute("email2", email2);
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute("error", "Lawyer not found");
            return "error_page"; // Redirect to an error page or handle accordingly
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while retrieving the lawyer data");
            return "error_page"; // Redirect to an error page for other exceptions
        }
        
        return "edit_lawyer"; // Render the edit lawyer form
    }

    @PostMapping("/lawyers/update")
    public String updateLawyer(@ModelAttribute Lawyer lawyer, 
                               @RequestParam String phoneNumber1, 
                               @RequestParam String phoneNumber2, 
                               @RequestParam String email1,
                               @RequestParam String email2) {
        // Update the lawyer information
        lawyerDAO.updateLawyer(lawyer);

        // Update phone numbers and emails
        List<String> newPhoneNumbers = new ArrayList<>();
        if (!phoneNumber1.isEmpty()) newPhoneNumbers.add(phoneNumber1);
        if (!phoneNumber2.isEmpty()) newPhoneNumbers.add(phoneNumber2);
        lawyerDAO.updateLawyerPhones(lawyer.getLawyerID(), newPhoneNumbers);

        List<String> newEmails = new ArrayList<>();
        if (!email1.isEmpty()) newEmails.add(email1);
        if (!email2.isEmpty()) newEmails.add(email2);
        lawyerDAO.updateLawyerEmails(lawyer.getLawyerID(), newEmails);

        return "redirect:/lawyers"; // Redirect to the lawyer list after updating
    }

    @PostMapping("/lawyers/delete/{id}")
    public String deleteLawyer(@PathVariable("id") Integer id) {
        lawyerDAO.deleteLawyer(id);
        lawyerDAO.deleteLawyerPhone(id); // Delete associated phone numbers
        lawyerDAO.deleteLawyerEmail(id); // Delete associated emails
        return "redirect:/lawyers"; // Redirect to the lawyer list after deletion
    }

    @GetMapping("/lawyers/search")
    public String searchLawyers(@RequestParam("query") String query, Model model) {
        List<Lawyer> lawyers = lawyerDAO.searchLawyers(query);
        model.addAttribute("lawyers", lawyers);
        model.addAttribute("searchQuery", query); // Add the search query to the model to show it on the view
        return "law_list"; // Return the view name for displaying the lawyer list
    }


    
}
