/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.config;

import com.plataforma.service.ActividadService;
import com.plataforma.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
/**
 *
 * @author xsf
 */

@Component
@RequiredArgsConstructor
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UsuarioService usuarioService;
    private final ActividadService actividadService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        String email = event.getAuthentication().getName();
        usuarioService.buscarPorEmail(email).ifPresent(usuario ->
                actividadService.registrar(usuario, "LOGIN", "Inicio de sesión exitoso"));
    }
}