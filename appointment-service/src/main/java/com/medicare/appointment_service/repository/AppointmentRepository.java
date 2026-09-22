package com.medicare.appointment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.appointment_service.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}