package com.medicare.appointment_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.appointment_service.client.BillingClient;
import com.medicare.appointment_service.client.DoctorClient;
import com.medicare.appointment_service.entity.Appointment;
import com.medicare.appointment_service.repository.AppointmentRepository;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;
    private final DoctorClient doctorClient;
    private final BillingClient billingClient;

    public AppointmentController(
            AppointmentRepository appointmentRepository,
            DoctorClient doctorClient,
            BillingClient billingClient) {

        this.appointmentRepository = appointmentRepository;
        this.doctorClient = doctorClient;
        this.billingClient = billingClient;
    }

    @PostMapping
    public Appointment bookAppointment(@RequestBody Appointment appointment) {

        // Check whether doctor exists
        if (!doctorClient.doctorExists(appointment.getDoctorId())) {
            throw new RuntimeException("Doctor not found");
        }

        // Book appointment
        appointment.setStatus("BOOKED");

        Appointment savedAppointment =
                appointmentRepository.save(appointment);

        // Create billing record
        billingClient.createBill(
                savedAppointment.getId(),
                savedAppointment.getPatientName(),
                500.0
        );

        return savedAppointment;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @DeleteMapping("/{id}")
    public String cancelAppointment(@PathVariable Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus("CANCELLED");
        appointmentRepository.save(appointment);

        return "Appointment cancelled successfully";
    }
}