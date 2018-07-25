package com.team4.app.model;

import org.hibernate.annotations.CollectionId;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "role")
    private String role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "reactivate_account_token")
    private String reactivateAccountToken;

    @Column(name = "confirm_account_token")
    private String confirmAccountToken;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name = "enabled")
    private boolean enabled;

    public User(User user) {
        this.role = user.role;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.username = user.username;
        this.password = user.password;
        this.longitude = user.longitude;
        this.latitude = user.latitude;
        this.passwordResetToken = user.passwordResetToken;
        this.confirmAccountToken = user.confirmAccountToken;
        this.reactivateAccountToken = user.reactivateAccountToken;
        this.enabled = user.enabled;

    }

    public User() {

    }

    public User(String role, String firstName, String lastName, String username, String password, Double longitude, Double latitude) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getReactivateAccountToken() {
        return reactivateAccountToken;
    }

    public void setReactivateAccountToken(String reactivateAccountToken) {
        this.reactivateAccountToken = reactivateAccountToken;
    }

    public String getConfirmAccountToken() {
        return confirmAccountToken;
    }

    public void setConfirmAccountToken(String confirmAccountToken) {
        this.confirmAccountToken = confirmAccountToken;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}