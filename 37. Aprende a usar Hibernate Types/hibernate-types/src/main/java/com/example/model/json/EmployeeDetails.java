package com.example.model.json;

import java.util.List;

public record EmployeeDetails(
        String biography,
        String location,
        String website,
        List<String> hobbies) implements java.io.Serializable{ }

