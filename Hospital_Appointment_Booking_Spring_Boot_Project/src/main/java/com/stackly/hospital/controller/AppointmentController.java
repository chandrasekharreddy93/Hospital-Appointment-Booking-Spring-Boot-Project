package com.stackly.hospital.controller;

import com.stackly.hospital.model.Appointment;
import com.stackly.hospital.service.AppointmentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService service;

    // Constructor injection
    public AppointmentController(AppointmentService service) {

        this.service = service;

    }

    // 1. Book a new appointment
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment bookAppointment(
            @Valid @RequestBody Appointment appointment) {

        return service.bookAppointment(appointment);
    }

    // 2. View all appointments
    @GetMapping
    public List<Appointment> getAllAppointments() {

        return service.getAllAppointments();
    }

    // 3. Find appointment by ID
    @GetMapping("/{id}")
    public Appointment getAppointmentById(
            @PathVariable("id") Long id) {

        return service.getAppointmentById(id);
    }

    // 4. Delete appointment
    @DeleteMapping("/{id}")
    public String deleteAppointment(
            @PathVariable("id") Long id) {

        return service.deleteAppointment(id);
    }

    // 5. Search appointments by patient name
    @GetMapping("/search")
    public List<Appointment> searchByPatientName(
            @RequestParam("keyword") String keyword) {

        return service.searchByPatientName(keyword);
    }

    // 6. Search appointments by doctor name
    @GetMapping("/doctor")
    public List<Appointment> searchByDoctorName(
            @RequestParam("name") String doctorName) {

        return service.searchByDoctorName(doctorName);
    }

    // 7. Search appointments by disease
    @GetMapping("/disease")
    public List<Appointment> searchByDisease(
            @RequestParam("name") String disease) {

        return service.searchByDisease(disease);
    }

    // 8. Search appointments by appointment date
    @GetMapping("/date")
    public List<Appointment> searchByDate(
            @RequestParam("date") LocalDate date) {

        return service.searchByDate(date);
    }

    // 9. Search appointments within a date range
    @GetMapping("/date-range")
    public List<Appointment> searchByDateRange(
            @RequestParam("startDate") LocalDate startDate,
            @RequestParam("endDate") LocalDate endDate) {

        return service.searchByDateRange(startDate, endDate);
    }

    // 10. Search appointments by doctor name and date
    @GetMapping("/doctor-date")
    public List<Appointment> searchByDoctorAndDate(
            @RequestParam("doctor") String doctorName,
            @RequestParam("date") LocalDate date) {

        return service.searchByDoctorAndDate(doctorName, date);
    }

    // 11. Update appointment details
    @PutMapping("/{id}")
    public Appointment updateAppointment(
            @PathVariable("id") Long id,
            @Valid @RequestBody Appointment appointment) {

        return service.updateAppointment(id, appointment);
    }
}
