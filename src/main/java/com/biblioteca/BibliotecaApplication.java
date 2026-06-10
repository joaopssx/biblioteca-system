package com.biblioteca;

import com.biblioteca.model.Bibliotecario;
import com.biblioteca.repository.BibliotecarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BibliotecaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDatabase(BibliotecarioRepository repository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (repository.count() == 0) {
                Bibliotecario admin = Bibliotecario.builder()
                        .username("admin")
                        .senha(passwordEncoder.encode("admin123"))
                        .nome("Administrador")
                        .build();
                repository.save(admin);
            }
        };
    }
}
