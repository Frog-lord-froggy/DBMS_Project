// // package com.example.demo.Controller;

// // import com.example.demo.model.CorpCourt;
// // import org.springframework.stereotype.Controller;
// // import org.springframework.ui.Model;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.ModelAttribute;
// // import org.springframework.web.bind.annotation.PostMapping;
// // import org.springframework.web.bind.annotation.RequestMapping;
// // import org.springframework.jdbc.core.JdbcTemplate;
// // import org.springframework.jdbc.core.RowMapper;

// // import java.sql.ResultSet;
// // import java.sql.SQLException;
// // import java.util.List;

// // @Controller
// // @RequestMapping("/corpCourt")
// // public class CorpCourtController {

// //     private final JdbcTemplate jdbcTemplate;

// //     public CorpCourtController(JdbcTemplate jdbcTemplate) {
// //         this.jdbcTemplate = jdbcTemplate;
// //     }

// //     @GetMapping("/form")
// //     public String showForm(Model model) {
// //         model.addAttribute("corpCourt", new CorpCourt());
// //         return "corpCourt";
// //     }

// //     @PostMapping("/save")
// //     public String saveCorpCourt(@ModelAttribute CorpCourt corpCourt) {
// //         String sql = "INSERT INTO CorpCourt (CourtName, CourtPincode, CourtState, CourtCity, FJName, MJName, LJName) "
// //                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
// //         jdbcTemplate.update(sql,
// //                 corpCourt.getCourtName(),
// //                 corpCourt.getCourtPincode(),
// //                 corpCourt.getCourtState(),
// //                 corpCourt.getCourtCity(),
// //                 corpCourt.getFjName(),
// //                 corpCourt.getMjName(),
// //                 corpCourt.getLjName());
    
// //         return "redirect:/corpCourt/form";
// //     }
    

// //     @GetMapping("/list")
// //     public String listCourts(Model model) {
// //         List<CorpCourt> corpCourts = getAllCourts();
// //         model.addAttribute("corpCourts", corpCourts);
// //         return "corpCourtList";
// //     }

// //     private List<CorpCourt> getAllCourts() {
// //         String sql = "SELECT * FROM CorpCourt";
// //         return jdbcTemplate.query(sql, new RowMapper<CorpCourt>() {
// //             @Override
// //             public CorpCourt mapRow(ResultSet rs, int rowNum) throws SQLException {
// //                 CorpCourt corpCourt = new CorpCourt();
// //                 corpCourt.setCourtCorpID(rs.getInt("CourtCorpID"));
// //                 corpCourt.setCourtName(rs.getString("CourtName"));
// //                 corpCourt.setCourtPincode(rs.getString("CourtPincode"));
// //                 corpCourt.setCourtState(rs.getString("CourtState"));
// //                 corpCourt.setCourtCity(rs.getString("CourtCity"));
// //                 corpCourt.setFjName(rs.getString("FJName"));
// //                 corpCourt.setMjName(rs.getString("MJName"));
// //                 corpCourt.setLjName(rs.getString("LJName"));
// //                 return corpCourt;
// //             }
// //         });
// //     }
// // }
package com.example.demo.Controller;


import com.example.demo.dao.CaseCourtDAO;
import com.example.demo.dao.CourtDAO;
import com.example.demo.model.CaseCourt;
import com.example.demo.model.Category;
import com.example.demo.model.Court;

import jakarta.persistence.criteria.CriteriaBuilder.Case;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/court")
public class CourtController {

    @Autowired
    private CourtDAO courtDAO;



    // Display all courts
    @GetMapping("/list")
    public String listCourts(Model model) {
        List<Court> courts = courtDAO.listCourts();
        model.addAttribute("courts", courts);
        return "CourtList"; // Name of the Thymeleaf template for displaying the court list
    }

    // Show form to add a new court
    @GetMapping("/add")
    public String showAddCourtForm(Model model) {
        model.addAttribute("court", new Court());
        return "Court"; // Name of the Thymeleaf template for the add court form
    }

    // Save a new court
    @PostMapping("/save")
    public String saveCourt(@ModelAttribute Court court) {
        courtDAO.saveCourt(court);
        return "redirect:/court/list"; // Redirect to the list of courts after saving
    }

    // Show form to edit an existing court
    @GetMapping("/edit/{id}")
    public String showEditCourtForm(@PathVariable Integer id, Model model) {
        Court court = courtDAO.getCourtById(id);
        model.addAttribute("court", court);
        return "editcourt"; // Name of the Thymeleaf template for the edit court form
    }

    // Update an existing court
    @PostMapping("/update")
    public String updateCourt(@ModelAttribute Court court) {
        courtDAO.updateCourt(court);
        return "redirect:/court/list"; // Redirect to the list of courts after updating
    }

    // Delete a court
    @GetMapping("/delete/{id}")
    public String deleteCourt(@PathVariable Integer id) {
        courtDAO.deleteCourt(id);
        return "redirect:/court/list"; // Redirect to the list of courts after deletion
    }

    @Autowired
    private CaseCourtDAO caseCourtDao;  // Use DAO to handle case-court relationships

    // Display the court assignment page
    @GetMapping("/assignCourt")
    public String showAssignCourtPage(@RequestParam("caseId") int caseId, 
                                       @RequestParam("x") String x, Model model) {
        List<Court> courts = courtDAO.listCourts(); // Fetch the list of courts from DAO
        model.addAttribute("courts", courts); // Add courts to the model
        model.addAttribute("caseId", caseId); // Add caseId to the model
        model.addAttribute("x", x); // Add x to the model
        return "assignCourt"; // Thymeleaf template name
    }

    // Handle court assignment
    @PostMapping("/assignCourt/save")
    public String assignCourts(@RequestParam("caseId") int caseId, 
                                @RequestParam(value = "courtIds", required = false) List<Integer> courtIds,
                                @RequestParam("x") int x) {
        // Check if courtIds is not null or empty
        if (courtIds != null && !courtIds.isEmpty()) {
            for (Integer courtId : courtIds) {
                CaseCourt caseCourt = new CaseCourt();
                caseCourt.setCaseID(caseId); // Set case ID
                caseCourt.setCourtID(courtId); // Set court ID
                caseCourt.setCatID(x);
                // Set CatID based on your logic if needed
                // caseCourt.setCatID(...); 

                caseCourtDao.save(caseCourt); // Use DAO to save the CaseCourt relationship
            }
        }
        return "redirect:/corporateCase/all"; // Redirect to corporate cases page after assignment
    }

    // Handle the form submission for assigning a case
  
}
