/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.controller;

import com.plataforma.domain.Rol;
import com.plataforma.domain.Usuario;
import com.plataforma.repository.RolRepository;
import com.plataforma.service.ActividadService;
import com.plataforma.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author xsf
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    private final UsuarioService usuarioService;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActividadService actividadService;

    @GetMapping
    public String listar(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        model.addAttribute("tituloPagina", "Administrar usuarios");
        model.addAttribute("usuarios", usuarios);
        return "usuario/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Usuario usuario = new Usuario();
        usuario.setActivo(true);
        model.addAttribute("tituloPagina", "Nuevo usuario");
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());
        return "usuario/form";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var opt = usuarioService.buscarPorId(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/usuarios";
        }
        model.addAttribute("tituloPagina", "Editar usuario");
        model.addAttribute("usuario", opt.get());
        model.addAttribute("roles", rolRepository.findAll());
        return "usuario/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario,
            @RequestParam Long rolId,
            @RequestParam(required = false) String passwordPlano) {

        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);

        boolean esNuevo = (usuario.getId() == null);

        if (passwordPlano != null && !passwordPlano.isBlank()) {
            usuario.setPassword(passwordEncoder.encode(passwordPlano));
        } else if (esNuevo) {
            throw new RuntimeException("La contraseña es obligatoria para nuevos usuarios");
        }

        Usuario guardado = usuarioService.guardar(usuario);

        // Auditoría básica
        String tipo = esNuevo ? "CREAR_USUARIO" : "EDITAR_USUARIO";
        String desc = (esNuevo ? "Creó" : "Editó") + " usuario " + guardado.getEmail();
        // Aquí podrías usar el usuario autenticado como autor; por ahora registra sobre el propio
        actividadService.registrar(guardado, tipo, desc);

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.buscarPorId(id).ifPresent(u
                -> actividadService.registrar(u, "ELIMINAR_USUARIO",
                        "Usuario eliminado: " + u.getEmail()));
        usuarioService.eliminarPorId(id);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        var opt = usuarioService.buscarPorId(id);
        if (opt.isEmpty()) {
            return "redirect:/admin/usuarios";
        }
        model.addAttribute("tituloPagina", "Detalle de usuario");
        model.addAttribute("usuario", opt.get());
        return "usuario/detalle";
    }

}
