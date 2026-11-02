package com.stackly.hospital.service;

import com.stackly.hospital.model.Appointment;
import com.stackly.hospital.exception.AppointmentNotFoundException;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class AppointmentService {

    // This ArrayList stores all hospital appointments.
    private final ArrayList<Appointment> appointments = new ArrayList<>();

    // Appointment ID starts from 1.
    private long nextId = 1L;

    // 1. Book a new appointment
    public synchronized Appointment bookAppointment(Appointment request) {

        // Create a new appointment and generate ID automatically.
        Appointment appointment = new Appointment(
                nextId++,
                request.getPatientName().trim(),
                request.getDoctorName().trim(),
                request.getDisease().trim(),
                request.getAppointmentDate()
        );

        // Store appointment details in ArrayList.
        appointments.add(appointment);

        // Return a copy of the booked appointment.
        return copyAppointment(appointment);
    }

    // 2. View all appointments
    public synchronized List<Appointment> getAllAppointments() {

        return appointments.stream()
                .map(this::copyAppointment)
                .toList();
    }

    // 3. Find appointment using ID
    public synchronized Appointment getAppointmentById(Long id) {

        Appointment appointment = appointments.stream()
                .filter(item -> item.getAppointmentId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found with ID: " + id
                        )
                );

        return copyAppointment(appointment);
    }

    // 4. Delete appointment
    public synchronized String deleteAppointment(Long id) {

        Appointment appointment = appointments.stream()
                .filter(item -> item.getAppointmentId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found with ID: " + id
                        )
                );

        appointments.remove(appointment);

        return "Appointment deleted successfully. ID: " + id;
    }

    // 5. Custom query: Search appointments by patient name
    public synchronized List<Appointment> searchByPatientName(String keyword) {

        checkNotBlank(keyword, "Patient name cannot be empty");

        String searchText = keyword.trim().toLowerCase(Locale.ROOT);

        return appointments.stream()
                .filter(appointment ->
                        appointment.getPatientName()
                                .toLowerCase(Locale.ROOT)
                                .contains(searchText)
                )
                .map(this::copyAppointment)
                .toList();
    }

    // 6. Custom query: Search appointments by doctor name
    public synchronized List<Appointment> searchByDoctorName(String doctorName) {

        checkNotBlank(doctorName, "Doctor name cannot be empty");

        return appointments.stream()
                .filter(appointment ->
                        appointment.getDoctorName()
                                .equalsIgnoreCase(doctorName.trim())
                )
                .map(this::copyAppointment)
                .toList();
    }

    // 7. Custom query: Search appointments by disease
    public synchronized List<Appointment> searchByDisease(String disease) {

        checkNotBlank(disease, "Disease cannot be empty");

        String searchText = disease.trim().toLowerCase(Locale.ROOT);

        return appointments.stream()
                .filter(appointment ->
                        appointment.getDisease()
                                .toLowerCase(Locale.ROOT)
                                .contains(searchText)
                )
                .map(this::copyAppointment)
                .toList();
    }

    // 8. Custom query: Search appointments by appointment date
    public synchronized List<Appointment> searchByDate(LocalDate date) {

        if (date == null) {
            throw new IllegalArgumentException(
                    "Appointment date cannot be empty"
            );
        }

        return appointments.stream()
                .filter(appointment ->
                        appointment.getAppointmentDate().equals(date)
                )
                .map(this::copyAppointment)
                .toList();
    }

    // 9. Custom query: Search appointments within a date range
    public synchronized List<Appointment> searchByDateRange(
            LocalDate startDate, LocalDate endDate) {

        if (startDate == null || endDate == null ||
                endDate.isBefore(startDate)) {

            throw new IllegalArgumentException(
                    "Invalid date range. End date must not be before start date"
            );
        }

        return appointments.stream()
                .filter(appointment ->
                        !appointment.getAppointmentDate().isBefore(startDate) &&
                        !appointment.getAppointmentDate().isAfter(endDate)
                )
                .map(this::copyAppointment)
                .toList();
    }

    // 10. Custom query: Search appointments by doctor name and date
    public synchronized List<Appointment> searchByDoctorAndDate(
            String doctorName, LocalDate date) {

        checkNotBlank(doctorName, "Doctor name cannot be empty");

        if (date == null) {
            throw new IllegalArgumentException(
                    "Appointment date cannot be empty"
            );
        }

        return appointments.stream()
                .filter(appointment ->
                        appointment.getDoctorName()
                                .equalsIgnoreCase(doctorName.trim())
                )
                .filter(appointment ->
                        appointment.getAppointmentDate().equals(date)
                )
                .map(this::copyAppointment)
                .toList();
    }

    // 11. Update existing appointment details
    public synchronized Appointment updateAppointment(
            Long id, Appointment request) {

        // Find the appointment using ID.
        Appointment existingAppointment = appointments.stream()
                .filter(appointment ->
                        appointment.getAppointmentId().equals(id)
                )
                .findFirst()
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found with ID: " + id
                        )
                );

        // Update the existing appointment details.
        existingAppointment.setPatientName(
                request.getPatientName().trim()
        );

        existingAppointment.setDoctorName(
                request.getDoctorName().trim()
        );

        existingAppointment.setDisease(
                request.getDisease().trim()
        );

        existingAppointment.setAppointmentDate(
                request.getAppointmentDate()
        );

        // Return updated appointment details.
        return copyAppointment(existingAppointment);
    }

    // Check whether the search input is empty.
    private void checkNotBlank(String value, String message) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
    }

    // Create a separate copy of appointment details.
    private Appointment copyAppointment(Appointment appointment) {

        return new Appointment(
                appointment.getAppointmentId(),
                appointment.getPatientName(),
                appointment.getDoctorName(),
                appointment.getDisease(),
                appointment.getAppointmentDate()
        );
    }
}
