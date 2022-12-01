package com.example.patient.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "patientid")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientES {

    @Id
    private String id;

    @NotNull(message = "Name cannot be null")
    @Pattern(regexp = "[A-Z]+",flags= Pattern.Flag.CASE_INSENSITIVE)
    private String  name;

    @Digits(integer = 2, fraction = 2)
    private int age;

    @Pattern(regexp = "^male$|^female$",flags=Pattern.Flag.CASE_INSENSITIVE)
    private String gender;

    @NotNull(message = "Phone Number cannot be null")
    @Pattern(regexp = "[0-9]{10}")
    private String phone;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Enter the email in the proper format")
    private String email;

    @NotNull(message = "Hid cannot be null")
    private int hid;

    @NotNull(message = "bloodgroup cannot be null")
    private String bloodgroup;

    @NotNull(message = "department cannot be null")
    private String department;
}
