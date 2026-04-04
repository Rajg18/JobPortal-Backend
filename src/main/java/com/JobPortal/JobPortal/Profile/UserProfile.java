package com.JobPortal.JobPortal.Profile;

import com.JobPortal.JobPortal.UserInfo;
import jakarta.persistence.*;

@Entity
    @Table(name = "user_profiles")
public class UserProfile {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String skills;
        private String location;
        private String phone;
        private int experience;

        @OneToOne
        @JoinColumn(name = "user_id", nullable = false, unique = true)
        private UserInfo user;

        // getters & setters


    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}

