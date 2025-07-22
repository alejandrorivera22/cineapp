package com.alex.cineapp.service;

import com.alex.cineapp.models.Pelicula;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPeliculaService {
    void insertar(Pelicula pelicula);
    List<Pelicula> buscarTodas();
    Pelicula buscarPorId(int idPelicula);
    List<String> buscarGeneros();
    List<Pelicula> buscarActiva();
    List<Pelicula> buscarPorFecha(Date fecha);
    Page<Pelicula> buscarTodas(Pageable pageable);
    void eliminar(int id);
}
