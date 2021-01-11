package com.ravi.boot.PatientApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger=LoggerFactory.getLogger(PatientApplication.class);
	
	@Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
	public static void main(String[] args) {
		logger.info("Application Started ");
		SpringApplication.run(PatientApplication.class, args);
	}

}
