package com.JobPortal.JobPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

// VIA_DTO ensures Page<T> serializes to a stable JSON structure across
// Spring Boot versions — content, pageable, totalElements, totalPages, etc.
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@SpringBootApplication
public class JobPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobPortalApplication.class, args);
    }
}
