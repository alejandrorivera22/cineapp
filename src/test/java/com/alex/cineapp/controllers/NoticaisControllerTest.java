package com.alex.cineapp.controllers;

import com.alex.cineapp.models.Noticia;
import com.alex.cineapp.service.INoticiasService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(controllers = NoticaisController.class)
class NoticaisControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    INoticiasService noticiasService;
    static final String GERENTE = "GERENTE";
    static final String RESOURCE_PATH = "/noticias";

    Noticia noticia;

    @BeforeEach
    void setUp() {
        noticia = new Noticia();
        noticia.setId(1);
        noticia.setTitulo("Noticia prueba");
        noticia.setEstatus("Activa");
        noticia.setDetalle("Detalle");
    }

    @Test
    @WithMockUser(roles = {GERENTE})
    @DisplayName("Deberia retornar vista con noticias")
    void mostrarIndex_DeberiaRetornarVistaConBanners() throws Exception {
        String uri = RESOURCE_PATH + "/" + "index";
        when(noticiasService.buscarTodas()).thenReturn(List.of(noticia));

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("noticias/listNoticias"))
                .andExpect(model().attributeExists("noticias"));
    }

    @Test
    @DisplayName("debería mostrar el formulario")
    @WithMockUser(roles = GERENTE)
    void crear_DeberiaRetornarFormulario() throws Exception {
        String uri = RESOURCE_PATH + "/" + "create";
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("noticias/formNoticia"));
    }

    @Test
    @WithMockUser(roles = GERENTE)
    @DisplayName("Debería guardar noticia y redirigir a index")
    void guardar_DeberiaGuardarNoticiaYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "save";
        mockMvc.perform(post(uri)
                        .param("titulo", "Noticia Test")
                        .param("estatus", "Activa")
                        .param("detalle", "Contenido de la noticia")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/noticias/index"));
    }

    @Test
    @WithMockUser(roles = {GERENTE})
    @DisplayName("debería eliminar la noticia")
    void eliminar_deberiaEliminarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/delete/" + 1;
        mockMvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/noticias/index"))
                .andExpect(flash().attribute("msg", "Noticia eliminada con exito"));

        verify(noticiasService).eliminar(1);
    }

    @Test
    @WithMockUser(roles = {GERENTE})
    @DisplayName("debería mostrar el banner para editar")
    void editar_deberiaMostrarFormulario() throws Exception {
        String uri = RESOURCE_PATH + "/edit/" + 1;
        when(noticiasService.buscarPorId(1)).thenReturn(noticia);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("noticias/formNoticia"))
                .andExpect(model().attributeExists("noticia"));
    }
}