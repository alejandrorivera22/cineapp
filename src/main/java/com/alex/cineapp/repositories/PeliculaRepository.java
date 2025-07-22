package com.alex.cineapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.alex.cineapp.models.Pelicula;


public interface PeliculaRepository extends JpaRepository<Pelicula, Integer>{
    
    List<Pelicula> findByEstatus_OrderByTitulo(String estatus);

}
