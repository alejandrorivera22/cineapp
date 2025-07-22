package com.alex.cineapp.service;

import java.util.List;

import com.alex.cineapp.models.Noticia;

public interface INoticiasService {

    void guardar(Noticia noticia);
    List<Noticia> buscarUltimas();
    List<Noticia> buscarTodas();
    Noticia buscarPorId(int idNoticia);
    void eliminar(int id);

}
