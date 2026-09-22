package com.medicare.billing_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.billing_service.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
}