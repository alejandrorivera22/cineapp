package com.alex.cineapp.service.jpa;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.cineapp.models.Perfil;

import com.alex.cineapp.repositories.PerfilesRepository;

import com.alex.cineapp.service.IPerfilesService;

@Service
public class PerfilServiceJpa implements IPerfilesService {
    @Autowired
    private PerfilesRepository perfilRepository;

    @Override
    public void guardar(Perfil usuario) {
        perfilRepository.save(usuario);
    }

    @Override
    public Perfil buscarPorId(int id) {
       Optional<Perfil> perfilDb = perfilRepository.findById(id);
       return perfilDb.get();
    }

    @Override
    public void eliminarPorId(int id) {
       perfilRepository.deleteById(id);
    }
}
