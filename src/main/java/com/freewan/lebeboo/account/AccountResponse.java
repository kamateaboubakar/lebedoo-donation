package com.freewan.lebeboo.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AccountResponse implements Serializable {
    private UUID accountId;
    private String email;
    private String firstName;
    private String lastName;
    @JsonProperty("login")
    private String phoneNumber;
}
