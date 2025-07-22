package com.alex.cineapp.service.jpa;

import com.alex.cineapp.models.Banner;
import com.alex.cineapp.models.Horario;
import com.alex.cineapp.models.Pelicula;
import com.alex.cineapp.repositories.HorarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HorarioServiceJpaTest extends ServiceSpec{

    @Mock
    HorarioRepository horarioRepository;

    @InjectMocks
    HorarioServiceJpa horarioServiceJpa;


    private Horario horario1;
    private Horario horario2;

    Pelicula pelicula1;
    Pelicula pelicula2;

    @BeforeEach
    void setUp() {
        pelicula1 = new Pelicula();
        pelicula1.setId(1);
        pelicula1.setTitulo("Pelicula1");

        pelicula2 = new Pelicula();
        pelicula2.setId(2);
        pelicula2.setTitulo("Pelicula2");

        horario1 = new Horario();
        horario1.setId(1);
        horario1.setFecha(new Date());
        horario1.setHora("15:00");
        horario1.setSala("Premium");
        horario1.setPrecio(50);
        horario1.setPelicula(pelicula1);

        horario2 = new Horario();
        horario2.setId(2);
        horario2.setFecha(new Date());
        horario2.setHora("15:00");
        horario2.setSala("Regular");
        horario2.setPrecio(25);
        horario2.setPelicula(pelicula2);
    }


    @Test
    @DisplayName("Deberia retornar los horarios dado el ID de la pelicula")
    void buscarPorIdPelicula__DeberiaRetornarHorariosFiltrados() {
        List<Horario> horarios = List.of(horario1);
        Date fecha = new Date();
        when(this.horarioRepository.findByPelicula_IdAndFechaOrderByHora(1, fecha)).thenReturn(horarios);
        List<Horario> resultado = this.horarioServiceJpa.buscarPorIdPelicula(1, fecha);
        assertNotNull(resultado);

        assertAll(
                () -> assertEquals(horarios.size(), resultado.size()),
                () -> assertEquals(horarios.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(horarios.get(0).getPelicula(), resultado.get(0).getPelicula()),
                () -> assertEquals(horarios.get(0).getSala(), resultado.get(0).getSala())
        );

    }

    @Test
    @DisplayName("Deberia insertar un nuevo horario")
    void insertar_DeberiaGuardarHorario() {
        when(horarioRepository.findAll())
                .thenReturn(List.of(horario1))     // Primera llamada
                .thenReturn(List.of(horario1, horario2)); // Segunda llamada

        assertEquals(1, horarioServiceJpa.buscarTodos().size(), "Se esperaba 1 elemento");
        horarioServiceJpa.insertar(horario2);
        assertEquals(2, horarioServiceJpa.buscarTodos().size(), "Se esperaba 2 elemntos");
    }

    @Test
    @DisplayName("Deberia retornar todos los horarios")
    void buscarTodos_DeberiaRetornarListaHorarios() {
        List<Horario> horarios = List.of(horario1, horario2);
        when(horarioRepository.findAll()).thenReturn(horarios);
        List<Horario> resultado = this.horarioServiceJpa.buscarTodos();
        assertNotNull(resultado);

        assertAll(
                () -> assertEquals(horarios.size(), resultado.size()),
                () -> assertEquals(horarios.get(0).getId(), resultado.get(0).getId()),
                () -> assertEquals(horarios.get(0).getPelicula(), resultado.get(0).getPelicula()),
                () -> assertEquals(horarios.get(0).getSala(), resultado.get(0).getSala())
        );
    }

    @Test
    @DisplayName("Deberia retornar los horarios paginados")
    void buscarTodosPaginado_DeberiaRetornarPaginaDeHorarios() {
        Page<Horario> pagina = new PageImpl<>(List.of(horario1));
        Pageable pageable = mock(Pageable.class);

        when(horarioRepository.findAll(pageable)).thenReturn(pagina);

        Page<Horario> resultado = horarioServiceJpa.buscarTodos(pageable);

        assertEquals(1, resultado.getTotalElements());
    }

    @Test
    @DisplayName("Deberia eliminar un horario dado su ID")
    void eliminarPorId() {
        when(horarioRepository.findAll())
                .thenReturn(List.of(horario1, horario2))
                .thenReturn(List.of(horario1));
        List<Horario> horariosAntes = horarioServiceJpa.buscarTodos();
        assertEquals(2, horariosAntes.size());

        int id = horariosAntes.get(0).getId();

        horarioServiceJpa.eliminarPorId(id);

        List<Horario> horariosDespues = horarioServiceJpa.buscarTodos();
        assertEquals(1, horariosDespues.size());
        verify(horarioRepository, times(1)).deleteById(anyInt());
    }

    @Test
    @DisplayName("Deberia retornar un horario dado el ID del la pelicula")
    void buscarPorId_DeberiaRetornarHorario() {
        when(horarioRepository.findById(1)).thenReturn(Optional.of(horario1));
        Horario horario = horarioServiceJpa.buscarPorId(1);
        assertNotNull(horario);
        assertEquals(horario1.getId(), horario.getId());
        assertEquals(horario1.getPelicula(), horario.getPelicula());
    }
}