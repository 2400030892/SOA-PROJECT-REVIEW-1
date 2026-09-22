package com.medicare.appointment_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class BillingClient {

    private final RestClient restClient;

    public BillingClient() {
        this.restClient = RestClient.create("http://localhost:8083");
    }

    public void createBill(Long appointmentId, String patientName, Double amount) {

        String requestBody = """
                {
                    "appointmentId": %d,
                    "patientName": "%s",
                    "amount": %.2f
                }
                """.formatted(appointmentId, patientName, amount);

        restClient.post()
                .uri("/bills")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .toBodilessEntity();
    }
}