package com.alex.cineapp.service;

import java.util.List;


import com.alex.cineapp.models.Usuario;

public interface IUsuarioService {
    void guardar(Usuario usuario);
    Usuario buscarPorId(int id);
    List<Usuario> buscarTodos();
    void eliminarPorId(int id);

}
