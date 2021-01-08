package com.ravi.boot.PatientApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.ravi.boot.PatientApplication.audit.AuditorAwareImpl;

import io.swagger.annotations.SwaggerDefinition;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PatientApplication {

	@Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
	public static void main(String[] args) {
		SpringApplication.run(PatientApplication.class, args);
	}

}
