package com.biblioteca.service;

import com.biblioteca.model.Bibliotecario;
import com.biblioteca.repository.BibliotecarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BibliotecarioDetailsService implements UserDetailsService {

    private final BibliotecarioRepository bibliotecarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Bibliotecario bibliotecario = bibliotecarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Bibliotecário não encontrado: " + username));

        return User.builder()
                .username(bibliotecario.getUsername())
                .password(bibliotecario.getSenha())
                .roles("ADMIN")
                .build();
    }
}
