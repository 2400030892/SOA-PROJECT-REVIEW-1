package com.medicare.appointment_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DoctorClient {

    private final RestClient restClient;

    public DoctorClient() {
        this.restClient = RestClient.create("http://localhost:8081");
    }

    public boolean doctorExists(Long doctorId) {

        try {
            restClient.get()
                    .uri("/doctors/{id}", doctorId)
                    .retrieve()
                    .toBodilessEntity();

            return true;

        } catch (Exception e) {
            return false;
        }
    }
}