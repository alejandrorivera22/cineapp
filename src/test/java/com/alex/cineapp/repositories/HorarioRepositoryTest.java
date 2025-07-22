package com.alex.cineapp.repositories;

import com.alex.cineapp.models.Horario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HorarioRepositoryTest extends RepositorySpec{

    @Autowired
    HorarioRepository horarioRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Test
    @DisplayName("Deberia devolver las pelicula dado su id y fecha")
    void findByPelicula_IdAndFechaOrderByHora() throws ParseException {
        Date date = sdf.parse("2024-03-02");
        List<Horario> peliculas = this.horarioRepository.findByPelicula_IdAndFechaOrderByHora(1, date);
        assertNotNull(peliculas, "No deberia ser nulo");
        int expecterdElements = 2;
        assertEquals(expecterdElements, peliculas.size(), "Se esperaba 2 elemento");
        assertEquals("18:00", peliculas.get(0).getHora());
    }

}