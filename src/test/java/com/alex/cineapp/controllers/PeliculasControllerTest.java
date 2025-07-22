package com.alex.cineapp.controllers;

import com.alex.cineapp.models.Detalle;
import com.alex.cineapp.models.Horario;
import com.alex.cineapp.models.Pelicula;
import com.alex.cineapp.service.IDetalleService;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PeliculasController.class)
class PeliculasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IPeliculaService peliculaService;

    @MockBean
    IDetalleService detalleService;

    static final String GERENTE = "GERENTE";
    static final String RESOURCE_PATH = "/peliculas";

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
    @WithMockUser(roles = {GERENTE})
    @DisplayName("Deberia retornar vista con peliculas")
    void mostrarIndex__deberiaMostrarLista() throws Exception {
        String uri = RESOURCE_PATH + "/" + "index";
        Page<Pelicula> page = new PageImpl<>(List.of(new Pelicula()));
        when(peliculaService.buscarTodas(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/peliculas/index"))
                .andExpect(status().isOk())
                .andExpect(view().name("peliculas/listPeliculas"))
                .andExpect(model().attributeExists("peliculas"));
    }

    @Test
    @DisplayName("debería mostrar el formulario")
    @WithMockUser(roles = GERENTE)
    void crear_deberiaRetornarVistaFormulario() throws Exception {
        String uri = RESOURCE_PATH + "/create";
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("peliculas/formPelicula"));
    }

    @Test
    @DisplayName("Debería guardar y redirigir")
    @WithMockUser(roles = GERENTE)
    void guardar_deberiaGuardarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "save";
        MockMultipartFile imagen = new MockMultipartFile("archivoImagen", "foto.jpg", "image/jpeg", "fake-image".getBytes());

        mockMvc.perform(multipart(uri)
                        .file(imagen)
                        .param("id", "0")
                        .param("titulo", "Título")
                        .param("estatus", "Activa")
                        .param("clasificacion", "B")
                        .param("genero", "Acción")
                        .param("duracion", "120")
                        .param("fechaEstreno", "22-07-25")
                        .param("detalle.sinopsis", "Una sinopsis...")
                        .param("detalle.director", "Director")
                        .param("detalle.actores", "Actor 1, Actor 2")
                        .param("detalle.trailer", "https://trailer.com")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/peliculas/index"))
                .andExpect(flash().attributeExists("msg"));
    }

    @Test
    @DisplayName("debería mostrar la pelicula para editar")
    @WithMockUser(roles = GERENTE)
    void editar_deberiaMostrarFormularioConDatos() throws Exception {
        String uri = RESOURCE_PATH + "/" + "edit" + "/" + 1;
        when(peliculaService.buscarPorId(1)).thenReturn(pelicula1);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("peliculas/formPelicula"))
                .andExpect(model().attributeExists("pelicula"));
    }

    @Test
    @DisplayName("Debería eliminar y redirigir")
    @WithMockUser(roles = GERENTE)
    void eliminar_deberiaEliminarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "delete" + "/" + 1;
        Detalle detalle = new Detalle();
        detalle.setId(10);
        pelicula1.setDetalle(detalle);

        when(peliculaService.buscarPorId(1)).thenReturn(pelicula1);

        mockMvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/peliculas/index"))
                .andExpect(flash().attributeExists("msg"));
    }
}