/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.plataforma.repository;

import com.plataforma.domain.Usuario;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 * @author xsf
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByRolNombre(String nombreRol);

    List<Usuario> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Usuario> findByEmailContainingIgnoreCaseOrNombreContainingIgnoreCase(
            String email, String nombre);

    List<Usuario> findAllByOrderByFechaCreacionDesc();
}
