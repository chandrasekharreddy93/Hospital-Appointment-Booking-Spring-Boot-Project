package com.stackly.hospital.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    // Appointment ID is generated automatically.
    private Long appointmentId;

    // Patient name should not be empty.
    @NotBlank(message = "Patient name is required")
    private String patientName;

    // Doctor name should not be empty.
    @NotBlank(message = "Doctor name is required")
    private String doctorName;

    // Disease field should not be empty.
    @NotBlank(message = "Disease field cannot be empty")
    private String disease;

    // Appointment date is required.
    @NotNull(message = "Appointment date is required")
    private LocalDate appointmentDate;

}
