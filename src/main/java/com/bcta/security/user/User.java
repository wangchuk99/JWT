package com.bcta.security.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sysuser")
public class User implements UserDetails {

    @Id
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
    private Date createdOn;
    private Date editedOn;

    @Enumerated(EnumType.STRING)
    @Column(name = "`role`")
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // Returning email here because I will be setting the JWT subject with email
    // Check JWTService for details
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
