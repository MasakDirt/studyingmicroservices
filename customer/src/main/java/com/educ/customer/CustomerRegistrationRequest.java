package com.educ.customer;

import lombok.ToString;

@ToString
public record CustomerRegistrationRequest(String firstName, String lastName, String email) {
}
