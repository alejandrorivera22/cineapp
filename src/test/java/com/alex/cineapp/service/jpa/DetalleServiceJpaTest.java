package com.alex.cineapp.service.jpa;

import com.alex.cineapp.models.Detalle;
import com.alex.cineapp.repositories.DetallesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DetalleServiceJpaTest extends ServiceSpec{

    @Mock
    private DetallesRepository detallesRepository;

    @InjectMocks
    DetalleServiceJpa detalleServiceJpa;

    Detalle detalle1;
    Detalle detalle2;

    @BeforeEach
    void setUp() {
        detalle1 = new Detalle();
        detalle1.setId(1);
        detalle1.setActores("Actor 1, Actor 2");
        detalle1.setDirector("Director 1");
        detalle1.setSinopsis("Sinopsis de a pelicula 1");
        detalle1.setTrailer("trasiler1");

        detalle2 = new Detalle();
        detalle2.setId(1);
        detalle2.setActores("Actor 3, Actor 4");
        detalle2.setDirector("Director 2");
        detalle2.setSinopsis("Sinopsis de a pelicula 2");
        detalle2.setTrailer("trasiler2");
    }

    @Test
    void insertar() {
        when(detallesRepository.findAll()).thenReturn(List.of(detalle1))
                .thenReturn(List.of(detalle1, detalle2));

        assertEquals(1, detallesRepository.findAll().size(), "Se esperaba 1 elemento");
        detalleServiceJpa.insertar(detalle2);
        assertEquals(2, detallesRepository.findAll().size(), "Se esperaban 2 elementos");
        verify(detallesRepository, times(1)).save(eq(detalle2));
    }

    @Test
    void eliminar() {
        when(detallesRepository.findAll()).thenReturn(List.of(detalle1, detalle2))
                .thenReturn(List.of(detalle1));

        assertEquals(2, detallesRepository.findAll().size(), "Se esperaban 2 elementos");
        detalleServiceJpa.eliminar(2);
        assertEquals(1, detallesRepository.findAll().size(), "Se esperaba 1 elemento");
        verify(detallesRepository, times(2)).findAll();
    }
}