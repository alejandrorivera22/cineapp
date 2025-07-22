package com.alex.cineapp.controllers;

import com.alex.cineapp.models.Banner;
import com.alex.cineapp.service.IBannerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BannerController.class)
class BannerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    IBannerService bannerService;

    private static final String RESOURCE_PATH = "/banners";

    Banner banner;

    final static String GERENTE = "GERENTE";

    @BeforeEach
    void setUp() {
        banner = new Banner();
        banner.setId(1);
        banner.setEstatus("Activo");
        banner.setTitulo("Banner1");
    }

    @Test
    @WithMockUser(roles = {GERENTE})
    @DisplayName("Deberia retornar vista con banners")
    void mostrarIndex_DeberiaRetornarVistaConBanners() throws Exception {
        String uri = RESOURCE_PATH + "/" + "index";
        when(bannerService.buscarTodos()).thenReturn(List.of(banner));

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("banners/listBanners"))
                .andExpect(model().attributeExists("banners"));
    }

    @Test
    @WithMockUser(roles = {GERENTE})
    @DisplayName("debería mostrar el formulario")
    void crear_deberiaRetornarFormulario() throws Exception {
        String uri = RESOURCE_PATH + "/" + "create";
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("banners/formBanner"));
    }

    @Test
    @WithMockUser(roles = {GERENTE})
    @DisplayName("Debería guardar banner y redirigir a index")
    void guardar_DeberiaGuardarBannerYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/" + "save";
        MockMultipartFile archivoImagen = new MockMultipartFile(
                "archivoImagen",
                "banner.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "contenido de imagen falso".getBytes()
        );

        mockMvc.perform(multipart(uri)
                        .file(archivoImagen)
                        .param("titulo", "Banner de prueba")
                        .param("estatus", "Activo")
                        .param("archivo", "")
                        .param("id", "0")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/banners/index"))
                .andExpect(flash().attributeExists("msg"));

        verify(bannerService).insertar(any(Banner.class));
    }


    @Test
    @WithMockUser(roles = {GERENTE})
    @DisplayName("debería mostrar el banner para editar")
    void edit_deberiaMostrarFormulario() throws Exception {
        String uri = RESOURCE_PATH + "/edit/" + 1;
        when(bannerService.buscarPorId(1)).thenReturn(banner);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("banners/formBanner"))
                .andExpect(model().attributeExists("banner"));
    }

    @Test
    @WithMockUser(roles = {GERENTE})
    @DisplayName("debería eliminar el banner")
    void eliminar_deberiaEliminarYRedirigir() throws Exception {
        String uri = RESOURCE_PATH + "/delete/" + 1;
        mockMvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/banners/index"))
                .andExpect(flash().attribute("msg", "El elemento fue eliminado"));

        verify(bannerService).eliminarPorId(1);
    }
}