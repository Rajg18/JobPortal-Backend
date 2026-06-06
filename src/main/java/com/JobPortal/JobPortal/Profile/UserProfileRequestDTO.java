package com.JobPortal.JobPortal.Profile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserProfileRequestDTO {

    // Email is taken from the JWT token in the controller — not sent in the body.

    @NotBlank(message = "Skills are required")
    private String skills;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Phone is required")
    private String phone;

    @Min(value = 0, message = "Experience cannot be negative")
    private int experience;

    public String getSkills()                { return skills; }
    public void   setSkills(String skills)   { this.skills = skills; }

    public String getLocation()                  { return location; }
    public void   setLocation(String location)   { this.location = location; }

    public String getPhone()               { return phone; }
    public void   setPhone(String phone)   { this.phone = phone; }

    public int  getExperience()              { return experience; }
    public void setExperience(int experience){ this.experience = experience; }
}
