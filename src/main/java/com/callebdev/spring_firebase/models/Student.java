package com.callebdev.spring_firebase.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Student {

    private String id = UUID.randomUUID().toString();
    private String firstName;
    private String lastName;
    private int age;
    private long phoneNumber;
    private String email;

    public Student(String firstName, String lastName, int age, long phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}