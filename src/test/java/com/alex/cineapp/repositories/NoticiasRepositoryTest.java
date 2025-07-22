package com.alex.cineapp.repositories;

import com.alex.cineapp.models.Noticia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoticiasRepositoryTest extends RepositorySpec{

    @Autowired
    NoticiasRepository noticiasRepository;

    @Test
    @DisplayName("Deberia devolver las tres utimas noticiasnoticias ")
    void findTop3ByEstatusOrderByIdDesc() {
        List<Noticia> noticiasRecientes = this.noticiasRepository.findTop3ByEstatusOrderByIdDesc("Activa");
        assertNotNull(noticiasRecientes);
        int expecterdElements = 3;
        assertEquals(expecterdElements, noticiasRecientes.size(), "Se esperaba 3 elemento");
    }
}