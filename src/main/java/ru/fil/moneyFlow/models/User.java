package ru.fil.moneyFlow.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_info")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Firstname should not be empty")
    @Size(min = 2, max = 30, message = "Length of firstname should be between 2 and 30 characters")
    private String firstname;

    @NotEmpty(message = "Lastname should not be empty")
    @Size(min = 2, max = 30, message = "Length of lastname should be between 2 and 30 characters")
    private String lastname;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be correct")
    private String email;

    @NotEmpty(message = "Firstname should not be empty")
    @Size(min = 4, message = "Length of password should be greater than 4 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;


    public void addTransaction(Transaction transaction) {
        if(transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

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
