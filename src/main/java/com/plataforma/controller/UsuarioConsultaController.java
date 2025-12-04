/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.controller;

import com.plataforma.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
/**
 *
 * @author xsf
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/usuarios/consultas")
public class UsuarioConsultaController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public String consultas(
            @RequestParam(required = false) String rol,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(required = false) String texto,
            Model model) {

        model.addAttribute("tituloPagina", "Consultas avanzadas de usuarios");

        // Por defecto: todos ordenados por fecha creación desc
        List<?> resultado = usuarioRepository.findAllByOrderByFechaCreacionDesc();

        if (rol != null && !rol.isBlank()) {
            resultado = usuarioRepository.findByRolNombre(rol);
        }

        if (desde != null && hasta != null) {
            // asegurar rango completo del día si necesitas; aquí se usa exacto
            resultado = usuarioRepository.findByFechaCreacionBetween(desde, hasta);
        }

        if (texto != null && !texto.isBlank()) {
            resultado = usuarioRepository
                    .findByEmailContainingIgnoreCaseOrNombreContainingIgnoreCase(texto, texto);
        }

        model.addAttribute("resultado", resultado);
        model.addAttribute("rol", rol);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        model.addAttribute("texto", texto);
        return "usuario/consultas";
    }
}