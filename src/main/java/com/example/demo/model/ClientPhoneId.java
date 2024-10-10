package com.example.demo.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ClientPhoneId implements Serializable {
    private Integer ClientId;
    private String phoneNumber;
    public String getClientId() {
        return null;
    }
    public String getPhoneNumber() {
        return null;
    }
}

