// // package com.example.demo;

// // import java.util.Arrays;
// // import java.util.List;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.dao.EmptyResultDataAccessException;
// // import org.springframework.http.HttpStatus;
// // import org.springframework.http.ResponseEntity;
// // import org.springframework.jdbc.core.BeanPropertyRowMapper;
// // import org.springframework.jdbc.core.JdbcTemplate;
// // import org.springframework.stereotype.Controller;
// // import org.springframework.ui.Model;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.ModelAttribute;
// // import org.springframework.web.bind.annotation.PathVariable;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.RequestParam;

// // import com.example.demo.model.User;


// // @Controller
// // public class MainController {
   
// //     @GetMapping("/register")
// //     public String showform(Model model) {
// //         User user = new User();
// //         model.addAttribute("user",user);
// //         List<String> professionList = Arrays.asList("Developer","Designer","Architect");
// //         List<String> genderList = Arrays.asList("Male","Female","Other");
// //         model.addAttribute("professionList", professionList);
// //         model.addAttribute("genderList",genderList);
// //         return "register_form";
// //     }

// //     @PostMapping("/register")
// //     public String submitForm(@ModelAttribute("user") User user) {
// //         System.out.println(user);
// //         saveUser(user); // Call the method to save user details
// //         return "register_success"; // Redirect to success page
// //     }

    
// //     @Autowired
// //     JdbcTemplate jdbcTemplate;

// //     // @GetMapping("/d")
// //     // public ResponseEntity<List<User>> getDoctors(){
// //     //     List<User> doctors = jdbcTemplate.query("SELECT * FROM User", new BeanPropertyRowMapper<User>(User.class));
// //     //     return new ResponseEntity<>(doctors,HttpStatus.OK);
// //     // }

// //     @GetMapping("/users")
// //     public String listUsers(Model model) {
// //         List<User> users = jdbcTemplate.query("SELECT * FROM User", new BeanPropertyRowMapper<>(User.class));
// //         model.addAttribute("users", users);
// //         return "user_list"; // Return a view name for displaying the user list
// //     }

// //     @GetMapping("/users/edit/{id}")
// //     public String showEditForm(@PathVariable("id") Integer id, Model model) {
// //         try {
// //             User user = jdbcTemplate.queryForObject(
// //                 "SELECT * FROM User WHERE id = ?", 
// //                 new BeanPropertyRowMapper<>(User.class), 
// //                 id
// //             );
// //             model.addAttribute("user", user);
// //         } catch (EmptyResultDataAccessException e) {
// //             model.addAttribute("error", "User not found");
// //             return "error_page"; // Redirect to an error page or handle accordingly
// //         }

// //         List<String> professionList = Arrays.asList("Developer", "Designer", "Architect");
// //         List<String> genderList = Arrays.asList("Male", "Female", "Other");
// //         model.addAttribute("professionList", professionList);
// //         model.addAttribute("genderList", genderList);
// //         return "edit_user"; // Render the edit user form
// //     }

// //     // Handle the form submission to update the user
// //     @PostMapping("/users/update")
// //     public String updateUser(@ModelAttribute("user") User user) {
// //         String query = "UPDATE User SET name = ?, email = ?, password = ?, gender = ?, profession = ?, birthday = ? WHERE id = ?";
// //         jdbcTemplate.update(query, user.getName(), user.getEmail(), user.getPassword(), user.getGender(), user.getProfession(), user.getBirthday(), user.getId());
// //         return "redirect:/users"; // Redirect to the user list after update
// //     }


// //     @PostMapping("/users/delete/{id}")
// //     public String deleteUser(@PathVariable("id") Long id) {
// //         jdbcTemplate.update("DELETE FROM User WHERE id = ?", id);
// //         return "redirect:/users"; // Redirect to the user list after deletion
// //     }

// //     private void saveUser(User user) {
// //         String query = "INSERT INTO user (name, email, password, gender, profession, birthday) VALUES (?, ?, ?, ?, ?, ?)";
// //         jdbcTemplate.update(query, user.getName(), user.getEmail(), user.getPassword(), user.getGender(), user.getProfession(), user.getBirthday());
// //     }

    
// // }
// // package com.example.demo;

// // import java.util.ArrayList;
// // import java.util.Arrays;
// // import java.util.HashSet;
// // import java.util.List;
// // import java.util.Set;

// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.dao.EmptyResultDataAccessException;
// // import org.springframework.jdbc.core.BeanPropertyRowMapper;
// // import org.springframework.jdbc.core.JdbcTemplate;
// // import org.springframework.stereotype.Controller;
// // import org.springframework.ui.Model;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.ModelAttribute;
// // import org.springframework.web.bind.annotation.PathVariable;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.RequestParam;

// // import com.example.demo.model.User;
// // import com.example.demo.model.UserPhone;

// // @Controller
// // public class MainController {
   
// //     @Autowired
// //     JdbcTemplate jdbcTemplate;

// //     @GetMapping("/register")
// //     public String showForm(Model model) {
// //         User user = new User();
// //         model.addAttribute("user", user);
// //         List<String> professionList = Arrays.asList("Developer", "Designer", "Architect");
// //         List<String> genderList = Arrays.asList("Male", "Female", "Other");
// //         model.addAttribute("professionList", professionList);
// //         model.addAttribute("genderList", genderList);
// //         return "register_form";
// //     }

// //     @PostMapping("/register")
// // public String submitForm(@ModelAttribute("user") User user, 
// //                          @RequestParam String phoneNumber1, 
// //                          @RequestParam(required = false) String phoneNumber2) {
// //     // Save the user first to get the ID
// //     saveUser(user);
    
// //     // After saving, retrieve the generated ID
// //     Integer userId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
    
// //     // Save phone numbers if provided
// //     if (phoneNumber1 != null && !phoneNumber1.isEmpty()) {
// //         saveUserPhone(userId, phoneNumber1);
// //     }
// //     if (phoneNumber2 != null && !phoneNumber2.isEmpty()) {
// //         saveUserPhone(userId, phoneNumber2);
// //     }

// //     return "register_success"; // Redirect to success page
// // }

// // private void saveUserPhone(Integer userId, String phoneNumber) {
// //     String query = "INSERT INTO UserPhone (userId, phoneNumber) VALUES (?, ?)";
// //     jdbcTemplate.update(query, userId, phoneNumber);
// // }

// //     @GetMapping("/users")
// //     public String listUsers(Model model) {
// //         List<User> users = jdbcTemplate.query("SELECT * FROM User", new BeanPropertyRowMapper<>(User.class));
// //         model.addAttribute("users", users);
// //         return "user_list"; // Return a view name for displaying the user list
// //     }

// //     @GetMapping("/users/edit/{id}")
// // public String showEditForm(@PathVariable("id") Integer id, Model model) {
// //     try {
// //         User user = jdbcTemplate.queryForObject(
// //             "SELECT * FROM User WHERE id = ?", 
// //             new BeanPropertyRowMapper<>(User.class), 
// //             id
// //         );
// //         model.addAttribute("user", user);
        
// //         // Fetch associated phone numbers
// //         List<UserPhone> userPhones = jdbcTemplate.query(
// //             "SELECT * FROM UserPhone WHERE userId = ?", 
// //             new BeanPropertyRowMapper<>(UserPhone.class), 
// //             id
// //         );
        
// //         model.addAttribute("userPhones", userPhones);

// //     } catch (EmptyResultDataAccessException e) {
// //         model.addAttribute("error", "User not found");
// //         return "error_page"; // Redirect to an error page or handle accordingly
// //     }

// //     List<String> professionList = Arrays.asList("Developer", "Designer", "Architect");
// //     List<String> genderList = Arrays.asList("Male", "Female", "Other");
// //     model.addAttribute("professionList", professionList);
// //     model.addAttribute("genderList", genderList);
// //     return "edit_user"; // Render the edit user form
// // }

    

// // @PostMapping("/users/update")
// // public String updateUser(@ModelAttribute("user") User user, 
// //                          @RequestParam(required = false) String phoneNumber1, 
// //                          @RequestParam(required = false) String phoneNumber2) {
// //     // Update user details
// //     String query = "UPDATE User SET name = ?, email = ?, password = ?, gender = ?, profession = ?, birthday = ? WHERE id = ?";
// //     jdbcTemplate.update(query, user.getName(), user.getEmail(), user.getPassword(), user.getGender(), user.getProfession(), user.getBirthday(), user.getId());

// //     // Update phone numbers
// //     updateUserPhones(user.getId(), phoneNumber1, phoneNumber2);

// //     return "redirect:/users"; // Redirect to the user list after update
// // }

// // private void updateUserPhones(Integer userId, String phoneNumber1, String phoneNumber2) {
// //     // Fetch existing phone numbers
// //     List<String> existingPhones = jdbcTemplate.queryForList("SELECT phoneNumber FROM UserPhone WHERE userId = ?", String.class, userId);

// //     // Create a set for quick lookup
// //     Set<String> existingPhoneSet = new HashSet<>(existingPhones);

// //     // Prepare new phone numbers
// //     List<String> newPhoneNumbers = new ArrayList<>();
// //     if (phoneNumber1 != null && !phoneNumber1.isEmpty()) {
// //         newPhoneNumbers.add(phoneNumber1);
// //     }
// //     if (phoneNumber2 != null && !phoneNumber2.isEmpty()) {
// //         newPhoneNumbers.add(phoneNumber2);
// //     }

// //     // Update or insert new phone numbers
// //     for (String newPhoneNumber : newPhoneNumbers) {
// //         if (!existingPhoneSet.contains(newPhoneNumber)) {
// //             // Insert new phone number if it doesn't exist
// //             String insertQuery = "INSERT INTO UserPhone (userId, phoneNumber) VALUES (?, ?)";
// //             jdbcTemplate.update(insertQuery, userId, newPhoneNumber);
// //         }
// //         existingPhoneSet.remove(newPhoneNumber); // Remove from the set to track which have been matched
// //     }

// //     // Delete any remaining phone numbers that were not included in the update
// //     for (String phoneToDelete : existingPhoneSet) {
// //         String deleteQuery = "DELETE FROM UserPhone WHERE userId = ? AND phoneNumber = ?";
// //         jdbcTemplate.update(deleteQuery, userId, phoneToDelete);
// //     }
// // }


// //     @PostMapping("/users/delete/{id}")
// //     public String deleteUser(@PathVariable("id") Long id) {
// //         jdbcTemplate.update("DELETE FROM User WHERE id = ?", id);
// //         jdbcTemplate.update("DELETE FROM UserPhone WHERE userId = ?", id); // Delete associated phone numbers
// //         return "redirect:/users"; // Redirect to the user list after deletion
// //     }

// //     private void saveUser(User user) {
// //         String query = "INSERT INTO User (name, email, password, gender, profession, birthday) VALUES (?, ?, ?, ?, ?, ?)";
// //         jdbcTemplate.update(query, user.getName(), user.getEmail(), user.getPassword(), user.getGender(), user.getProfession(), user.getBirthday());
// //     }

// // }
// package com.example.demo.Controller;

// import java.util.ArrayList;
// import java.util.HashSet;
// import java.util.List;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.EmptyResultDataAccessException;
// import org.springframework.jdbc.core.BeanPropertyRowMapper;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.example.demo.model.Client;
// import com.example.demo.model.ClientEmail;
// import com.example.demo.model.ClientPhone;

// @Controller
// public class MainController {
   
//     @Autowired
//     JdbcTemplate jdbcTemplate;

//     @GetMapping("/register")
//     public String showForm(Model model) {
//         Client client = new Client();
//         model.addAttribute("client", client);
//         return "register_form";
//     }

//     @PostMapping("/register")
//     public String submitForm(@ModelAttribute("client") Client client, 
//                              @RequestParam String phoneNumber1, 
//                              @RequestParam(required = false) String phoneNumber2,
//                              @RequestParam String email1,
//                              @RequestParam(required = false) String email2) {
//         // Save the client first to get the ID
//         saveClient(client);
        
//         // After saving, retrieve the generated ID
//         Integer clientId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        
//         // Save phone numbers if provided
//         if (phoneNumber1 != null && !phoneNumber1.isEmpty()) {
//             saveClientPhone(clientId, phoneNumber1);
//         }
//         if (phoneNumber2 != null && !phoneNumber2.isEmpty()) {
//             saveClientPhone(clientId, phoneNumber2);
//         }

//         // Save emails if provided
//         if (email1 != null && !email1.isEmpty()) {
//             saveClientEmail(clientId, email1);
//         }
//         if (email2 != null && !email2.isEmpty()) {
//             saveClientEmail(clientId, email2);
//         }

//         return "redirect:/clients"; // Redirect to success page
//     }

//     private void saveClient(Client client) {
//         String query = "INSERT INTO Client (FName, MName, LName, Occupation, DateOfBirth, Spouse, Address, Children) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//         jdbcTemplate.update(query, client.getFName(), client.getMName(), client.getLName(), client.getOccupation(), client.getDateOfBirth(), client.getSpouse(), client.getAddress(), client.getChildren());
//     }

//     private void saveClientPhone(Integer clientId, String phoneNumber) {
//         String query = "INSERT INTO ClientPhone (clientId, phoneNumber) VALUES (?, ?)";
//         jdbcTemplate.update(query, clientId, phoneNumber);
//     }

//     private void saveClientEmail(Integer clientId, String email) {
//         String query = "INSERT INTO ClientEmail (clientId, emailAddress) VALUES (?, ?)";
//         jdbcTemplate.update(query, clientId, email);
//     }

//     @GetMapping("/clients")
//     public String listClients(Model model) {
//         List<Client> clients = jdbcTemplate.query("SELECT * FROM Client", new BeanPropertyRowMapper<>(Client.class));
//         model.addAttribute("clients", clients);
//         return "client_list"; // Return a view name for displaying the client list
//     }

//     @GetMapping("/clients/edit/{id}")
//     public String showEditForm(@PathVariable("id") Integer id, Model model) {
//         try {
//             Client client = jdbcTemplate.queryForObject(
//                 "SELECT * FROM Client WHERE clientId = ?", 
//                 new BeanPropertyRowMapper<>(Client.class), 
//                 id
//             );
//             model.addAttribute("client", client);
            
//             // Fetch associated phone numbers and emails
//             List<ClientPhone> clientPhones = jdbcTemplate.query(
//                 "SELECT * FROM ClientPhone WHERE clientId = ?", 
//                 new BeanPropertyRowMapper<>(ClientPhone.class), 
//                 id
//             );
//             List<ClientEmail> clientEmails = jdbcTemplate.query(
//                 "SELECT * FROM ClientEmail WHERE ClientId = ?", 
//                 new BeanPropertyRowMapper<>(ClientEmail.class), 
//                 id
//             );
//             model.addAttribute("clientPhones", clientPhones);
//             model.addAttribute("clientEmails", clientEmails);

//        // Safely retrieve phone numbers and emails
//        String phoneNumber1 = clientPhones.size() > 0 ? clientPhones.get(0).getPhoneNumber() : "";
//        String phoneNumber2 = clientPhones.size() > 1 ? clientPhones.get(1).getPhoneNumber() : "";
//        String email1 = clientEmails.size() > 0 && clientEmails.get(0).getId() != null 
//                        ? clientEmails.get(0).getemail() : ""; // Use the getEmail method
//        String email2 = clientEmails.size() > 1 && clientEmails.get(1).getId() != null 
//                        ? clientEmails.get(1).getemail() : ""; // Use the getEmail method

//        model.addAttribute("phoneNumber1", phoneNumber1);
//        model.addAttribute("phoneNumber2", phoneNumber2);
//        model.addAttribute("email1", email1);
//        model.addAttribute("email2", email2);
//         } catch (EmptyResultDataAccessException e) {
//             model.addAttribute("error", "Client not found");
//             return "error_page"; // Redirect to an error page or handle accordingly
//         }
//         return "edit_client"; // Render the edit client form
//     }

//     @PostMapping("/clients/update")
//     public String updateClient(@ModelAttribute("client") Client client, 
//                                @RequestParam(required = false) String phoneNumber1, 
//                                @RequestParam(required = false) String phoneNumber2,
//                                @RequestParam(required = false) String email1,
//                                @RequestParam(required = false) String email2) {
//         // Update client details
//         String query = "UPDATE Client SET FName = ?, MName = ?, LName = ?, Occupation = ?, DateOfBirth = ?, Spouse = ?, Address = ?, Children = ? WHERE clientId = ?";
//         jdbcTemplate.update(query, client.getFName(), client.getMName(), client.getLName(), client.getOccupation(), client.getDateOfBirth(), client.getSpouse(), client.getAddress(), client.getChildren(), client.getClientId());

//         // Update phone numbers and emails
//         updateClientPhones(client.getClientId(), phoneNumber1, phoneNumber2);
//         updateClientEmails(client.getClientId(), email1, email2);

//         return "redirect:/clients"; // Redirect to the client list after update
//     }

  

//     @PostMapping("/clients/delete/{id}")
//     public String deleteClient(@PathVariable("id") Long id) {
//         jdbcTemplate.update("DELETE FROM Client WHERE clientId = ?", id);
//         jdbcTemplate.update("DELETE FROM ClientPhone WHERE clientId = ?", id); // Delete associated phone numbers
//         jdbcTemplate.update("DELETE FROM ClientEmail WHERE clientId = ?", id); // Delete associated emails
//         return "redirect:/clients"; // Redirect to the client list after deletion
//     }

//     private void updateClientPhones(Integer clientId, String phoneNumber1, String phoneNumber2) {
//         // Fetch existing phone numbers
//         List<String> existingPhones = jdbcTemplate.queryForList(
//             "SELECT phoneNumber FROM ClientPhone WHERE clientId = ?", 
//             String.class, 
//             clientId
//         );
    
//         // Create a set for quick lookup
//         Set<String> existingPhoneSet = new HashSet<>(existingPhones);
    
//         // Prepare new phone numbers
//         List<String> newPhoneNumbers = new ArrayList<>();
//         if (phoneNumber1 != null && !phoneNumber1.isEmpty()) {
//             newPhoneNumbers.add(phoneNumber1);
//         }
//         if (phoneNumber2 != null && !phoneNumber2.isEmpty()) {
//             newPhoneNumbers.add(phoneNumber2);
//         }
    
//         // Update or insert new phone numbers
//         for (String newPhoneNumber : newPhoneNumbers) {
//             if (!existingPhoneSet.contains(newPhoneNumber)) {
//                 // Insert new phone number if it doesn't exist
//                 String insertQuery = "INSERT INTO ClientPhone (clientId, phoneNumber) VALUES (?, ?)";
//                 jdbcTemplate.update(insertQuery, clientId, newPhoneNumber);
//             }
//             existingPhoneSet.remove(newPhoneNumber); // Remove from the set to track which have been matched
//         }
    
//         // Delete any remaining phone numbers that were not included in the update
//         for (String phoneToDelete : existingPhoneSet) {
//             String deleteQuery = "DELETE FROM ClientPhone WHERE clientId = ? AND phoneNumber = ?";
//             jdbcTemplate.update(deleteQuery, clientId, phoneToDelete);
//         }
//     }
    
//     private void updateClientEmails(Integer clientId, String email1, String email2) {
//         // Fetch existing email addresses
//         List<String> existingEmails = jdbcTemplate.queryForList(
//             "SELECT emailAddress FROM ClientEmail WHERE clientId = ?", 
//             String.class, 
//             clientId
//         );
    
//         // Create a set for quick lookup
//         Set<String> existingEmailSet = new HashSet<>(existingEmails);
    
//         // Prepare new email addresses
//         List<String> newEmails = new ArrayList<>();
//         if (email1 != null && !email1.isEmpty()) {
//             newEmails.add(email1);
//         }
//         if (email2 != null && !email2.isEmpty()) {
//             newEmails.add(email2);
//         }
    
//         // Update or insert new email addresses
//         for (String newEmail : newEmails) {
//             if (!existingEmailSet.contains(newEmail)) {
//                 // Insert new email if it doesn't exist
//                 String insertQuery = "INSERT INTO ClientEmail (clientId, emailAddress) VALUES (?, ?)";
//                 jdbcTemplate.update(insertQuery, clientId, newEmail);
//             }
//             existingEmailSet.remove(newEmail); // Remove from the set to track which have been matched
//         }
    
//         // Delete any remaining email addresses that were not included in the update
//         for (String emailToDelete : existingEmailSet) {
//             String deleteQuery = "DELETE FROM ClientEmail WHERE clientId = ? AND emailAddress = ?";
//             jdbcTemplate.update(deleteQuery, clientId, emailToDelete);
//         }
//     }
    
    
// }

package com.example.demo.Controller;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.ClientDAO;
import com.example.demo.model.Client;



@Controller
public class ClientController {

    @Autowired
    private ClientDAO clientDAO;

    @GetMapping("/register")
    public String showForm(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "register_form";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("client") Client client,
                             @RequestParam String phoneNumber1,
                             @RequestParam(required = false) String phoneNumber2,
                             @RequestParam String email1,
                             @RequestParam(required = false) String email2) {
        // Save the client first to get the ID
        clientDAO.saveClient(client);

        // After saving, retrieve the generated ID
        Integer clientId = clientDAO.getLastInsertId();

        // Save phone numbers if provided
        if (phoneNumber1 != null && !phoneNumber1.isEmpty()) {
            clientDAO.saveClientPhone(clientId, phoneNumber1);
        }
        if (phoneNumber2 != null && !phoneNumber2.isEmpty()) {
            clientDAO.saveClientPhone(clientId, phoneNumber2);
        }

        // Save emails if provided
        if (email1 != null && !email1.isEmpty()) {
            clientDAO.saveClientEmail(clientId, email1);
        }
        if (email2 != null && !email2.isEmpty()) {
            clientDAO.saveClientEmail(clientId, email2);
        }

        return "redirect:/clients"; // Redirect to success page
    }

    @GetMapping("/clients")
    public String listClients(Model model) {
        List<Client> clients = clientDAO.listClients();
        model.addAttribute("clients", clients);
        return "client_list"; // Return a view name for displaying the client list
    }


    @GetMapping("/clients/edit/{id}")
public String showEditForm(@PathVariable("id") Integer id, Model model) {
    try {
        // Fetch the client by ID
        Client client = clientDAO.getClientById(id);
        model.addAttribute("client", client);

        // Fetch associated phone numbers and emails
        List<String> clientPhones = clientDAO.getClientPhones(id); // List of phone numbers as Strings
        List<String> clientEmails = clientDAO.getClientEmails(id); // List of ClientEmail objects
        
        model.addAttribute("clientPhones", clientPhones);
        model.addAttribute("clientEmails", clientEmails);

        // Safely retrieve phone numbers and emails
        String phoneNumber1 = clientPhones.size() > 0 ? clientPhones.get(0) : ""; // Correctly get the phone number
        String phoneNumber2 = clientPhones.size() > 1 ? clientPhones.get(1) : ""; // Correctly get the second phone number
        String email1 = clientEmails.size() > 0 ? clientEmails.get(0) : ""; // Correctly get the first email
        String email2 = clientEmails.size() > 1 ? clientEmails.get(1) : ""; // Correctly get the second email


        // Add the phone numbers and emails to the model
        model.addAttribute("phoneNumber1", phoneNumber1);
        model.addAttribute("phoneNumber2", phoneNumber2);
        model.addAttribute("email1", email1);
        model.addAttribute("email2", email2);
    } catch (EmptyResultDataAccessException e) {
        model.addAttribute("error", "Client not found");
        return "error_page"; // Redirect to an error page or handle accordingly
    } catch (Exception e) {
        model.addAttribute("error", "An error occurred while retrieving the client data");
        return "error_page"; // Redirect to an error page for other exceptions
    }
    
    return "edit_client"; // Render the edit client form
}


    @PostMapping("/clients/update")
public String updateClient(@ModelAttribute Client client, @RequestParam String phoneNumber1, 
                           @RequestParam String phoneNumber2, @RequestParam String email1,
                           @RequestParam String email2) {
    // Update the client information
    clientDAO.updateClient(client);

    // Update phone numbers and emails
    List<String> newPhoneNumbers = new ArrayList<>();
    if (!phoneNumber1.isEmpty()) newPhoneNumbers.add(phoneNumber1);
    if (!phoneNumber2.isEmpty()) newPhoneNumbers.add(phoneNumber2);
    clientDAO.updateClientPhones(client.getClientId(), newPhoneNumbers);

    List<String> newEmails = new ArrayList<>();
    if (!email1.isEmpty()) newEmails.add(email1);
    if (!email2.isEmpty()) newEmails.add(email2);
    clientDAO.updateClientEmails(client.getClientId(), newEmails);

    return "redirect:/clients"; // Redirect to the client list after updating
}


    @PostMapping("/clients/delete/{id}")
    public String deleteClient(@PathVariable("id") Integer id) {
        clientDAO.deleteClient(id);
        clientDAO.deleteClientPhone(id); // Delete associated phone numbers
        clientDAO.deleteClientEmail(id); // Delete associated emails
        return "redirect:/clients"; // Redirect to the client list after deletion
    }


    @GetMapping("/clients/search")
public String searchClients(@RequestParam("query") String query, Model model) {
    List<Client> clients = clientDAO.searchClients(query);
    model.addAttribute("clients", clients);
    model.addAttribute("searchQuery", query); // Add the search query to the model to show it on the view
    return "client_list"; // Return the view name for displaying the client list
}

}
