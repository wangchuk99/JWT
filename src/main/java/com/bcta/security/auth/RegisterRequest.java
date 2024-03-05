package com.bcta.security.auth;

import com.bcta.security.user.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String id;
    private String username;
    private String password;
    private String fullName;
    private String email;

    private String contactNumber;
    private Integer status;
    private String rememberToken;
    private String cmnProcuringAgencyId;
    private String createdBy;
    private String editedBy;
}
