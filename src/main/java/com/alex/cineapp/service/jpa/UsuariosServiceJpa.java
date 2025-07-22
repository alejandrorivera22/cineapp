package com.alex.cineapp.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.cineapp.models.Usuario;
import com.alex.cineapp.repositories.UsuariosRepository;
import com.alex.cineapp.service.IUsuarioService;

@Service
public class UsuariosServiceJpa implements IUsuarioService{

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Override
    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario buscarPorId(int id) {
        Optional<Usuario> usuarioDb = usuarioRepository.findById(id);
        return usuarioDb.get(); 
    }

    @Override
    public void eliminarPorId(int id) {
        usuarioRepository.deleteById(id);
    }
    
}
