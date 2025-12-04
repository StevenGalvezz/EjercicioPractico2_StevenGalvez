/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.plataforma.service;

import com.plataforma.domain.Usuario;

import java.util.List;
import java.util.Optional;
/**
 *
 * @author xsf
 */
public interface UsuarioService {

    List<Usuario> listarTodos();

    Optional<Usuario> buscarPorId(Long id);

    Optional<Usuario> buscarPorEmail(String email);

    Usuario guardar(Usuario usuario);

    void eliminarPorId(Long id);
}