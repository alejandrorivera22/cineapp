package com.alex.cineapp.repositories;

import com.alex.cineapp.models.Noticia;
import com.alex.cineapp.models.Pelicula;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeliculaRepositoryTest extends RepositorySpec{

    @Autowired
    PeliculaRepository peliculaRepository;

    @Test
    @DisplayName("Deberia devolver as peliculas dado su estado Activo")
    void findByEstatus_OrderByTitulo() {
        List<Pelicula> peliculas = this.peliculaRepository.findByEstatus_OrderByTitulo("Activa");
        assertNotNull(peliculas);
        int expecterdElements = 6;
        assertEquals(expecterdElements, peliculas.size(), "Se esperaba 3 elemento");
       assertTrue(
               peliculas.stream().allMatch(
                       p -> "Activa".equals(p.getEstatus())),
                       "Todas las peliculas deberiasn de ser ACtivas"
       );
    }
}