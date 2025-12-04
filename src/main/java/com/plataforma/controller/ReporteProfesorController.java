/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author xsf
 */
@Controller
public class ReporteProfesorController {

    @GetMapping("/profesor/reportes")
    public String reportesProfesor(Model model) {
        model.addAttribute("tituloPagina", "Reportes del profesor");
        return "profesor/reportes";
    }
}