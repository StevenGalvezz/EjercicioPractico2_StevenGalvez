/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.controller;

import com.plataforma.domain.Rol;
import com.plataforma.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author xsf
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/roles")
public class RolController {

    private final RolRepository rolRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("tituloPagina", "Gestión de roles");
        model.addAttribute("roles", rolRepository.findAll());
        return "rol/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("tituloPagina", "Nuevo rol");
        model.addAttribute("rol", new Rol());
        return "rol/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var opt = rolRepository.findById(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/roles";
        }
        model.addAttribute("tituloPagina", "Editar rol");
        model.addAttribute("rol", opt.get());
        return "rol/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Rol rol) {
        rolRepository.save(rol);
        return "redirect:/admin/roles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        rolRepository.deleteById(id);
        return "redirect:/admin/roles";
    }
}