package com.alex.cineapp.service.jpa;

import com.alex.cineapp.models.Horario;
import com.alex.cineapp.models.Noticia;
import com.alex.cineapp.models.Pelicula;
import com.alex.cineapp.repositories.HorarioRepository;
import com.alex.cineapp.repositories.PeliculaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PelisculaServiceJpaTest extends ServiceSpec{

    @Mock
    PeliculaRepository peliculaRepository;

    @Mock
    HorarioRepository horariosRepo;

    @InjectMocks
    PelisculaServiceJpa pelisculaService;

    Pelicula pelicula1;
    Pelicula pelicula2;
    Pelicula pelicula3;
    Horario horario;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @BeforeEach
    void setUp() {
        pelicula1 = new Pelicula();
        pelicula1.setId(1);
        pelicula1.setTitulo("Pelicula1");
        pelicula1.setEstatus("Activa");
        pelicula1.setEstatus("Activa");

        pelicula2 = new Pelicula();
        pelicula2.setId(2);
        pelicula2.setTitulo("Pelicula2");
        pelicula2.setEstatus("Activa");

        pelicula3 = new Pelicula();
        pelicula3.setId(3);
        pelicula3.setTitulo("Pelicula3");
        pelicula3.setEstatus("Activa");

        horario = new Horario();
        horario.setId(1);
        horario.setPelicula(pelicula1);
    }

    @Test
    @DisplayName("Debería Guardar una pelicula")
    void insertar_DeberiaInsertarPelicula() {
        when(peliculaRepository.findAll())
                .thenReturn(List.of(pelicula1))     // Primera llamada
                .thenReturn(List.of(pelicula1,pelicula2)); // Segunda llamada

        assertEquals(1, pelisculaService.buscarTodas().size());
        pelisculaService.insertar(pelicula2);
        assertEquals(2, pelisculaService.buscarTodas().size());
    }

    @Test
    @DisplayName("Debería regresar todas las peliculas")
    void buscarTodas_DeberiaRetornarListaPeliculas() {
        when(peliculaRepository.findAll()).thenReturn(List.of(pelicula1, pelicula2, pelicula3));
        List<Pelicula> peliculas = pelisculaService.buscarTodas();
        assertEquals(3, peliculas.size());
    }

    @Test
    @DisplayName("Debería retornar una pelicula por ID")
    void buscarPorId_DeberiaRetornarPelicula() {
        when(peliculaRepository.findById(1)).thenReturn(Optional.of(pelicula1));
        Pelicula peliculaResultado = pelisculaService.buscarPorId(1);
        assertNotNull(peliculaResultado);
        assertEquals(pelicula1.getTitulo() , peliculaResultado.getTitulo());
    }

    @Test
    @DisplayName("Deberia retornar todos los generos")
    void buscarGeneros_DeberiaRetornarListaGeneros() {
        List<String> generosEsperados = new LinkedList<>();
        generosEsperados.add("Accion");
        generosEsperados.add("Aventuras");
        generosEsperados.add("Clasicas");
        generosEsperados.add("Drama");
        generosEsperados.add("Comedia");
        generosEsperados.add("Terror");
        generosEsperados.add("Infantil");
        generosEsperados.add("Accion y Aventura");
        generosEsperados.add("Romantica");
        generosEsperados.add("Ciencia ficcion");

        List<String> generosResultado = pelisculaService.buscarGeneros();
        assertNotNull(generosResultado);

        assertEquals(generosEsperados.size(), generosResultado.size());
    }

    @Test
    void eliminar() {
        when(peliculaRepository.findAll())
                .thenReturn(List.of(pelicula1, pelicula2))
                .thenReturn(List.of(pelicula1));
        List<Pelicula> peliculasAntes = pelisculaService.buscarTodas();
        assertEquals(2, peliculasAntes.size());

        int id = peliculasAntes.get(0).getId();

        pelisculaService.eliminar(id);

        List<Pelicula> peliculasDespues = pelisculaService.buscarTodas();
        assertEquals(1, peliculasDespues.size());
        verify(peliculaRepository, times(1)).deleteById(anyInt());
    }

    @Test
    @DisplayName("Deberia retornar las peliculas paginados")
    void buscarTodasPaginado_DeberiaRetornarPaginaDePeliculas() {
        Page<Pelicula> pagina = new PageImpl<>(List.of(pelicula1));
        Pageable pageable = mock(Pageable.class);

        when(peliculaRepository.findAll(pageable)).thenReturn(pagina);

        Page<Pelicula> resultado = pelisculaService.buscarTodas(pageable);

        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    @DisplayName("Deberia retornar las peliculas con estatus activa")
    void buscarActiva_DeberiaRetornarListaPeliculasActivas() {
        when(peliculaRepository.findByEstatus_OrderByTitulo("Activa")).thenReturn(List.of(pelicula1, pelicula2, pelicula3));
        List<Pelicula> peliculas = pelisculaService.buscarActiva();
        assertEquals(3, peliculas.size());
        assertTrue(peliculas.stream().allMatch(
                p -> "Activa".equals(p.getEstatus())
        ));
    }

    @Test
    @DisplayName("Deberia retornar las peliculas por fecha")
    void buscarPorFecha_DeberiaRetornarListaPeliculasPorFecha() throws ParseException {
        Date date = sdf.parse("2024-03-02");

        when(horariosRepo.findByFecha(date)).thenReturn(List.of(horario));
        List<Pelicula> peliculas = pelisculaService.buscarPorFecha(date);

        assertNotNull(peliculas);
        assertEquals(1, peliculas.size());

        assertAll(
                () -> assertEquals(pelicula1.getTitulo(), peliculas.get(0).getTitulo())
        );

    }
}