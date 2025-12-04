/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.controller;

import com.plataforma.service.ActividadService;
import com.plataforma.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author xsf
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/actividad")
public class ActividadController {

    private final ActividadService actividadService;
    private final UsuarioService usuarioService;

    @GetMapping
    public String listar(@RequestParam(required = false) Long usuarioId, Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("usuarioId", usuarioId);

        if (usuarioId != null) {
            var usuario = usuarioService.buscarPorId(usuarioId)
                    .orElse(null);
            model.addAttribute("actividad",
                    usuario != null ? actividadService.listarPorUsuario(usuario) : java.util.List.of());
        } else {
            model.addAttribute("actividad", actividadService.listarTodas());
        }

        model.addAttribute("tituloPagina", "Actividad de usuarios");
        return "actividad/listado";
    }
}