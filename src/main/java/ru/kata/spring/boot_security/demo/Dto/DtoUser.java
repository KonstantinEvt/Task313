package ru.kata.spring.boot_security.demo.Dto;

import java.util.HashSet;
import java.util.Set;

public class DtoUser {


    private String firstName;


    private String lastName;


    private String email;

    private String password;

    private String username;
    private Set<String> roles = new HashSet<>();

    public DtoUser() {
    }

    public DtoUser(String password, String username, Set<String> roles) {
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    public DtoUser(String firstName, String lastName, String email, String password, String username, Set<String> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }


}
