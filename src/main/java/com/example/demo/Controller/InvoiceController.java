package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Display form to add a new invoice
    // @GetMapping("/add")
    // public String showAddInvoiceForm(@RequestParam("caseId") int caseId, Model model) {
    //     String sql = "SELECT * FROM CorporateCase WHERE CorporateCaseID = ?";
    //     List<Map<String, Object>> corporateCases = jdbcTemplate.queryForList(sql, caseId);
        
    //     if (corporateCases.isEmpty()) {
    //         return "error";  // Handle error when CorporateCase is not found
    //     }

    //     model.addAttribute("corporateCase", corporateCases.get(0));
    //     return "addinvoCorp";  // Thymeleaf template to add the invoice
    // }

    // // Handle form submission for adding a new invoice
    // @PostMapping("/save")
    // public String saveInvoice(@RequestParam int corporateCaseID,
    //                           @RequestParam double amount,
    //                           @RequestParam String dueDate,
    //                           @RequestParam String status) {
    //     String query = "INSERT INTO InvoiceCorp (CorporateCaseID, InvoiceDate, Amount, DueDate, Status) VALUES (?, ?, ?, ?, ?)";
    //     jdbcTemplate.update(query, corporateCaseID, LocalDate.now(), amount, dueDate, status);
    //     return "redirect:/invoice/list";
    // }

    @GetMapping("/add")
    public String showAddInvoiceForm(@RequestParam("caseId") int caseId, @RequestParam("catId") int catId, Model model) {
        // Create a query to fetch the appropriate case based on the catId
        String sql = "";
    
        switch (catId) {
            case 1: // Assuming 1 is for CorporateCase
                sql = "SELECT * FROM CorporateCase WHERE CorporateCaseID = ?";
                break;
            case 2: // Assuming 2 is for CriminalCase
                sql = "SELECT * FROM CriminalCase WHERE CriminalCaseID = ?";
                break;
            case 3: // Assuming 3 is for CivilCase
                sql = "SELECT * FROM CivilCase WHERE CivilCaseID = ?";
                break;
            case 4: // Assuming 4 is for MatrimonialCase
                sql = "SELECT * FROM MatrimonialCase WHERE MatrimonialCaseID = ?";
                break;
            default:
                return "error";  // Handle error for unknown case type
        }
    
        List<Map<String, Object>> cases = jdbcTemplate.queryForList(sql, caseId);
        
        if (cases.isEmpty()) {
            return "error";  // Handle error when case is not found
        }
    
        model.addAttribute("caseDetails", cases.get(0)); // Add case details to the model
        // No need to add catId again, it can be accessed directly from the method parameter
        model.addAttribute("caseId", caseId);
    model.addAttribute("catId", catId);
        return "addinvoCorp";  // Thymeleaf template to add the invoice
    }
    

// Handle form submission for adding a new invoice
@PostMapping("/save")
public String saveInvoice(@RequestParam int caseId,
                          @RequestParam int catId,
                          @RequestParam double amount,
                          @RequestParam String dueDate,
                          @RequestParam String status) {
    // Determine the table to insert based on the catId
    String query = "INSERT INTO Invoice (CaseID, CatID, InvoiceDate, Amount, DueDate, Status) VALUES (?, ?, ?, ?, ?, ?)";
    
    // Insert the new invoice using caseId and catId
    jdbcTemplate.update(query, caseId, catId, LocalDate.now(), amount, dueDate, status);
    return "redirect:/invoice/list"; // Redirect to the invoice list after saving
}


    // Display list of invoices and cases without invoices
    @GetMapping("/list")
public String listInvoices(Model model) {
    // Query to get invoices from all four case types, including CatID
    String invoiceQuery = "SELECT i.InvoiceID, i.Amount, i.InvoiceDate, i.Status, i.CaseID, i.CatID, c.CaseType, " +
                          "CASE " +
                          "WHEN cc.CorporateCaseID IS NOT NULL THEN 'CorporateCase' " +
                          "WHEN cr.CriminalCaseID IS NOT NULL THEN 'CriminalCase' " +
                          "WHEN civ.CivilCaseID IS NOT NULL THEN 'CivilCase' " +
                          "WHEN mc.MatrimonialCaseID IS NOT NULL THEN 'MatrimonialCase' " +
                          "END as CaseCategory, " +
                          "CASE " +
                          "WHEN cc.CorporateCaseID IS NOT NULL THEN cc.CaseDesc " +
                          "WHEN cr.CriminalCaseID IS NOT NULL THEN cr.CaseDesc " +
                          "WHEN civ.CivilCaseID IS NOT NULL THEN civ.CaseDesc " +
                          "WHEN mc.MatrimonialCaseID IS NOT NULL THEN mc.CaseDesc " +
                          "END as CaseDesc " +
                          "FROM Invoice i " +
                          "JOIN Category c ON i.CatID = c.CatID " +
                          "LEFT JOIN CorporateCase cc ON i.CaseID = cc.CorporateCaseID " +
                          "LEFT JOIN CriminalCase cr ON i.CaseID = cr.CriminalCaseID " +
                          "LEFT JOIN CivilCase civ ON i.CaseID = civ.CivilCaseID " +
                          "LEFT JOIN MatrimonialCase mc ON i.CaseID = mc.MatrimonialCaseID";

    // Get all invoices with the respective case descriptions from the relevant case type
    List<Map<String, Object>> invoices = jdbcTemplate.queryForList(invoiceQuery);

    // Query to fetch cases without invoices across all case types, including CatID
   // Query to fetch cases without invoices across all case types, including CatID
String caseQuery = "(SELECT cc.CorporateCaseID AS CaseID, cc.CaseDesc, 'CorporateCase' AS CaseCategory, 1 AS CatID FROM CorporateCase cc " +
"LEFT JOIN Invoice i ON cc.CorporateCaseID = i.CaseID AND i.CatID=1 WHERE i.InvoiceID IS NULL) " +
"UNION " +
"(SELECT cr.CriminalCaseID AS CaseID, cr.CaseDesc, 'CriminalCase' AS CaseCategory, 4 AS CatID FROM CriminalCase cr " +
"LEFT JOIN Invoice i ON cr.CriminalCaseID = i.CaseID AND i.CatID=4 WHERE i.InvoiceID IS NULL) " +
"UNION " +
"(SELECT civ.CivilCaseID AS CaseID, civ.CaseDesc, 'CivilCase' AS CaseCategory, 3 AS CatID FROM CivilCase civ " +
"LEFT JOIN Invoice i ON civ.CivilCaseID = i.CaseID AND i.CatID=3 WHERE i.InvoiceID IS NULL) " +
"UNION " +
"(SELECT mc.MatrimonialCaseID AS CaseID, mc.CaseDesc, 'MatrimonialCase' AS CaseCategory, 2 AS CatID FROM MatrimonialCase mc " +
"LEFT JOIN Invoice i ON mc.MatrimonialCaseID = i.CaseID AND i.CatID=2 WHERE i.InvoiceID IS NULL)";


    // Fetch cases without invoices
    List<Map<String, Object>> casesWithoutInvoices = jdbcTemplate.queryForList(caseQuery);

    // Add both lists to the model
    model.addAttribute("invoices", invoices);
    model.addAttribute("casesWithoutInvoices", casesWithoutInvoices);

    // Return the Thymeleaf template name
    return "invoicelist";  // Template to display invoices
}



    @PostMapping("/updateStatus")
public String updateInvoiceStatus(@RequestParam("invoiceID") int invoiceID, 
                                  @RequestParam("status") String status) {
    String sql = "UPDATE Invoice SET Status = ? WHERE InvoiceID = ?";
    jdbcTemplate.update(sql, status, invoiceID);
    return "redirect:/invoice/list";  // Redirect back to the invoice list
}
}
