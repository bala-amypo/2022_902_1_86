package com.example.demo.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
dto/AuthResponse.java
package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}