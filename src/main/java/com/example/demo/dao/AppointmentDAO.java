package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Appointment;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Repository
public class AppointmentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save a new appointment
    public void saveAppointment(Appointment appointment) {
        String query = "INSERT INTO Appointment (CatID, CaseID, LawyerID, ClientID, AppointmentDate, AppointmentTime, Location) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query,
            appointment.getCatID(), // Assuming you have a method to get CatID directly from Appointment
            appointment.getCaseID(), // Assuming you have a direct case ID
            appointment.getLawyerID(), // Assuming you have a direct lawyer ID
            appointment.getClientID(), // Assuming you have a direct client ID
            Date.valueOf(appointment.getAppointmentDate()), // Convert LocalDate to SQL Date
            Time.valueOf(appointment.getAppointmentTime()), // Convert LocalTime to SQL Time
            appointment.getLocation()
        );
    }

    // Get a list of all appointments
    public List<Appointment> listAppointments() {
        String query = "SELECT * FROM Appointment";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(rs.getInt("AppointmentID"));
            appointment.setCaseID(rs.getInt("CaseID"));
            appointment.setLawyerID(rs.getInt("LawyerID"));
            appointment.setClientID(rs.getInt("ClientID"));
            appointment.setAppointmentDate(rs.getDate("AppointmentDate").toLocalDate());
            appointment.setAppointmentTime(rs.getTime("AppointmentTime").toLocalTime());
            appointment.setLocation(rs.getString("Location"));
            appointment.setCatID(rs.getInt("CatID")); // Assuming this field exists in Appointment
            return appointment;
        });
    }

    // Get appointment by ID
    public Appointment getAppointmentById(Integer id) {
        String query = "SELECT * FROM Appointment WHERE AppointmentID = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(rs.getInt("AppointmentID"));
            appointment.setCaseID(rs.getInt("CaseID"));
            appointment.setLawyerID(rs.getInt("LawyerID"));
            appointment.setClientID(rs.getInt("ClientID"));
            appointment.setAppointmentDate(rs.getDate("AppointmentDate").toLocalDate());
            appointment.setAppointmentTime(rs.getTime("AppointmentTime").toLocalTime());
            appointment.setLocation(rs.getString("Location"));
            appointment.setCatID(rs.getInt("CatID")); // Assuming this field exists in Appointment
            return appointment;
        }, id);
    }

    // Update an appointment
    public void updateAppointment(Appointment appointment) {
        String query = "UPDATE Appointment SET CatID = ?, CaseID = ?, LawyerID = ?, ClientID = ?, AppointmentDate = ?, AppointmentTime = ?, Location = ? WHERE AppointmentID = ?";
        jdbcTemplate.update(query,
            appointment.getCatID(), // Assuming you have a method to get CatID directly from Appointment
            appointment.getCaseID(), // Assuming you have a direct case ID
            appointment.getLawyerID(), // Assuming you have a direct lawyer ID
            appointment.getClientID(), // Assuming you have a direct client ID
            Date.valueOf(appointment.getAppointmentDate()), // Convert LocalDate to SQL Date
            Time.valueOf(appointment.getAppointmentTime()), // Convert LocalTime to SQL Time
            appointment.getLocation(), // Location from Appointment object
            appointment.getAppointmentID() // Appointment ID for the update
        );
    }

    // Delete an appointment
    public void deleteAppointment(Integer id) {
        String query = "DELETE FROM Appointment WHERE AppointmentID = ?";
        jdbcTemplate.update(query, id);
    }

    // Search for appointments by location or lawyer (example use case)
    public List<Appointment> searchAppointments(String keyword) {
        String query = "SELECT * FROM Appointment WHERE Location LIKE ? OR LawyerID IN (SELECT LawyerID FROM Lawyer WHERE LName LIKE ?)";
        String searchKeyword = "%" + keyword + "%"; // Add wildcards for partial matching
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(rs.getInt("AppointmentID"));
            appointment.setCaseID(rs.getInt("CaseID"));
            appointment.setLawyerID(rs.getInt("LawyerID"));
            appointment.setClientID(rs.getInt("ClientID"));
            appointment.setAppointmentDate(rs.getDate("AppointmentDate").toLocalDate());
            appointment.setAppointmentTime(rs.getTime("AppointmentTime").toLocalTime());
            appointment.setLocation(rs.getString("Location"));
            appointment.setCatID(rs.getInt("CatID")); // Assuming this field exists in Appointment
            return appointment;
        }, searchKeyword, searchKeyword);
    }

    // Update appointment time and date
    public void updateAppointmentDateTime(Integer appointmentID, Date newDate, Time newTime) {
        String query = "UPDATE Appointment SET AppointmentDate = ?, AppointmentTime = ? WHERE AppointmentID = ?";
        jdbcTemplate.update(query, newDate, newTime, appointmentID);
    }

    // Update appointment location
    public void updateAppointmentLocation(Integer appointmentID, String newLocation) {
        String query = "UPDATE Appointment SET Location = ? WHERE AppointmentID = ?";
        jdbcTemplate.update(query, newLocation, appointmentID);
    }
}
