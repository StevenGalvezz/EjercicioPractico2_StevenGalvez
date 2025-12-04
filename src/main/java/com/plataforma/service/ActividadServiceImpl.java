/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.plataforma.service;

import com.plataforma.domain.ActividadUsuario;
import com.plataforma.domain.Usuario;
import com.plataforma.repository.ActividadUsuarioRepository;
import com.plataforma.service.ActividadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
/**
 *
 * @author xsf
 */

@Service
@RequiredArgsConstructor
public class ActividadServiceImpl implements ActividadService {

    private final ActividadUsuarioRepository actividadRepository;

    @Override
    public ActividadUsuario registrar(Usuario usuario, String tipo, String descripcion) {
        ActividadUsuario act = new ActividadUsuario();
        act.setUsuario(usuario);
        act.setTipo(tipo);
        act.setDescripcion(descripcion);
        act.setFechaHora(LocalDateTime.now());
        return actividadRepository.save(act);
    }

    @Override
    public List<ActividadUsuario> listarTodas() {
        return actividadRepository.findAll();
    }

    @Override
    public List<ActividadUsuario> listarPorUsuario(Usuario usuario) {
        return actividadRepository.findByUsuario(usuario);
    }
}