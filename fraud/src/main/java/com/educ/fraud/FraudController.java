package com.educ.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fraud-check")
@AllArgsConstructor
@Slf4j
public class FraudController {
    private final FraudCheckService fraudCheckService;

    @GetMapping ("/{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable int customerId) {
        boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);
        log.info("fraud check request for customer {} and he/she is {}",
                customerId, isFraudulentCustomer ? "fraud" : "not fraud");
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
