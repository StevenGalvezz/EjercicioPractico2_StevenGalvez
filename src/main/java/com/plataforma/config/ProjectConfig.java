/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.config;

import com.plataforma.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author xsf
 */

@Configuration
@RequiredArgsConstructor
public class ProjectConfig {

    private final UsuarioService usuarioService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public UserDetailsService userDetailsService() {
        return email -> usuarioService.buscarPorEmail(email)
                .map(u -> org.springframework.security.core.userdetails.User.builder()
                        .username(u.getEmail())
                        .password(u.getPassword())
                        .roles(u.getRol().getNombre()) // ADMIN, PROFESOR, ESTUDIANTE
                        .disabled(!u.getActivo())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }*/
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            System.out.println("Intentando login con: " + email);

            return usuarioService.buscarPorEmail(email)
                    .map(u -> {
                        System.out.println("Usuario encontrado: " + u.getEmail());
                        System.out.println("Password en BD: " + u.getPassword());
                        System.out.println("Rol: " + u.getRol().getNombre());

                        return org.springframework.security.core.userdetails.User.builder()
                                .username(u.getEmail())
                                .password(u.getPassword())
                                .roles(u.getRol().getNombre())
                                .disabled(!u.getActivo())
                                .build();
                    })
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
            UserDetailsService uds) throws Exception {

        http.userDetailsService(uds);
        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/webjars/**", "/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/profesor/**").hasRole("PROFESOR")
                .requestMatchers("/estudiante/**").hasRole("ESTUDIANTE")
                .anyRequest().authenticated()
        )
                .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
                );

        return http.build();
    }
}
