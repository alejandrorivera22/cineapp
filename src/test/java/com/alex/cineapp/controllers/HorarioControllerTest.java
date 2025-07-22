package com.alex.cineapp.controllers;

import com.alex.cineapp.models.Horario;
import com.alex.cineapp.models.Pelicula;
import com.alex.cineapp.service.IHorariosService;
import com.alex.cineapp.service.IPeliculaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;

@WebMvcTest(HorarioController.class)
class HorarioControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPeliculaService peliculaService;

    @MockBean
    private IHorariosService horariosService;

    Pelicula pelicula;
    Horario horario;

    static final String GERENTE = "GERENTE";
    static final String RESOURCE_PATH = "/horarios";


    @BeforeEach
    void setUp() {
        pelicula = new Pelicula();
        pelicula.setId(1);
        pelicula.setTitulo("Ejemplo");

        horario = new Horario();
        horario.setId(1);
        horario.setPelicula(pelicula);
        horario.setFecha(new Date());
        horario.setHora("15:00");
    }

    @Test
    @WithMockUser(roles = GERENTE)
    @DisplayName("deberia retornar la vista con horarios paginados")
    void mostrarHorarios_DeberiaMostrarVistaConHorarios() throws Exception {
        String uri = RESOURCE_PATH + "/" + "index";
        Page<Horario> page = new PageImpl<>(List.of(horario));

        when(horariosService.buscarTodos(any(Pageable.class))).thenReturn(page);
        when(peliculaService.buscarActiva()).thenReturn(List.of(pelicula));

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("horarios/listHorarios"))
                .andExpect(model().attributeExists("horarios"));
    }

    @Test
    @WithMockUser(roles = GERENTE)
    @DisplayName("deberia mostrar el formulario vacío")
    void crear_deberiaRetornarVistaFormulario() throws Exception {
        String uri = RESOURCE_PATH + "/" + "create";
        when(peliculaService.buscarActiva()).thenReturn(List.of(pelicula));

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("horarios/formHorario"));
    }

    @Test
    @WithMockUser(roles = GERENTE)
    @DisplayName("deberia redirigir con datos válidos")
    void guardar_deberiaGuardarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "save";
        mockMvc.perform(post(uri)
                        .param("id", "0")
                        .param("fecha", "21-07-2025")
                        .param("hora", "15:00")
                        .param("sala", "Sala 1")
                        .param("precio", "50")
                        .param("pelicula.id", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/horarios/index"));
    }

    @Test
    @WithMockUser(roles = GERENTE)
    @DisplayName("Debería regresar a formulario si falta algún campo")
    void guardar_deberiaFallarValidacion() throws Exception {
        String uri = RESOURCE_PATH + "/" + "save";

        mockMvc.perform(post(uri)
                        .param("id", "0")
                        .param("fecha", "21-07-2025")
                        // falta hora, sala y precio
                        .param("pelicula.id", "1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("horarios/formHorario"))
                .andExpect(model().attributeHasFieldErrors("horario", "hora", "sala", "precio"));
    }



    @Test
    @WithMockUser(roles = GERENTE)
    @DisplayName("deberia mostrar datos del horario")
    void editar_deberiaRetornarFormularioConDatos() throws Exception {
        String uri = RESOURCE_PATH + "/" + "edit" + "/" + 1;
        when(horariosService.buscarPorId(1)).thenReturn(horario);
        when(peliculaService.buscarActiva()).thenReturn(List.of(pelicula));

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("horarios/formHorario"))
                .andExpect(model().attributeExists("horario"));
    }

    @Test
    @WithMockUser(roles = GERENTE)
    @DisplayName("deberia eliminar y redirigir")
    void eliminar_deberiaEliminarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "delete" + "/" + 1;
        mockMvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/horarios/index"));
    }


}