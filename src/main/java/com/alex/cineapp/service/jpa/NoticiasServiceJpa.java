package com.alex.cineapp.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.cineapp.models.Noticia;
import com.alex.cineapp.repositories.NoticiasRepository;
import com.alex.cineapp.service.INoticiasService;

@Service
public class NoticiasServiceJpa implements INoticiasService{

    @Autowired
    private NoticiasRepository noticiaRepository;

    @Override
    public void guardar(Noticia noticia) {
        noticiaRepository.save(noticia);
    }

    @Override
    public List<Noticia> buscarUltimas() {
        return noticiaRepository.findTop3ByEstatusOrderByIdDesc("Activa");
    }

    @Override
    public List<Noticia> buscarTodas() {
       return noticiaRepository.findAll();
    }

    @Override
    public Noticia buscarPorId(int idNoticia) {
       Optional<Noticia> noticiaDb = noticiaRepository.findById(idNoticia);
        if (noticiaDb.isPresent()) {
            return noticiaDb.get();
        }
        return noticiaDb.orElseThrow();
    }

    @Override
    public void eliminar(int id) {
        noticiaRepository.deleteById(id);
    }

    
    
}
