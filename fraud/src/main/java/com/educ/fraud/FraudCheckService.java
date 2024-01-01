package com.educ.fraud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudCheckService {
    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;
    private final RestTemplate restTemplate;

    public boolean isFraudulentCustomer(int customerId) {
        boolean isFraudster = checkCustomer(customerId);

        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(isFraudster)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return isFraudster;
    }

    private boolean checkCustomer(int customerId) {
        String email = getCustomerEmail(customerId);
        return isUserFraudByEmail(email);
    }

    private String getCustomerEmail(int customerId) {
        return restTemplate.getForObject(
                        "http://localhost:8080/api/v1/customers/{customerId}",
                        CustomerResponse.class,
                        customerId
                )
                .email();
    }

    private boolean isUserFraudByEmail(String email) {
        return email.contains("@fraud.") || email.contains("@mail.ru");
    }
}
