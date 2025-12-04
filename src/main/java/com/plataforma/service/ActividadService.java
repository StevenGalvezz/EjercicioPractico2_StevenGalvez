/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.plataforma.service;

import com.plataforma.domain.ActividadUsuario;
import com.plataforma.domain.Usuario;

import java.util.List;
/**
 *
 * @author xsf
 */
public interface ActividadService {

    ActividadUsuario registrar(Usuario usuario, String tipo, String descripcion);

    List<ActividadUsuario> listarTodas();

    List<ActividadUsuario> listarPorUsuario(Usuario usuario);
}