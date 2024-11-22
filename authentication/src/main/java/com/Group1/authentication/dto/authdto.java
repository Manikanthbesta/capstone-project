package com.Group1.authentication.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class authdto {
    private String userName;
    private String password;
    @Pattern(regexp = "^(CUSTOMER|VENDOR)$", message = "Role must be either 'Customer' or 'Vendor'")
    private String role;
}
