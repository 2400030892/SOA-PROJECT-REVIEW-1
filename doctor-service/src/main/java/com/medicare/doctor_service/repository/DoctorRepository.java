package com.medicare.doctor_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.doctor_service.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}