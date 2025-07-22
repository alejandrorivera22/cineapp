package com.alex.cineapp.controllers;

import com.alex.cineapp.models.Perfil;
import com.alex.cineapp.models.Usuario;
import com.alex.cineapp.service.IPerfilesService;
import com.alex.cineapp.service.IUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuariosController.class)
class UsuariosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUsuarioService serviceUsuario;

    @MockBean
    private IPerfilesService servicePerfil;

    @MockBean
    private PasswordEncoder encoder;

    static final String GERENTE = "GERENTE";
    static final String RESOURCE_PATH = "/usuarios";

    @Test
    @DisplayName("debería mostrar formulario de usuario")
    @WithMockUser(roles = "ADMIN")
    void crear() throws Exception {
        String uri = RESOURCE_PATH + "/create";
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("usuarios/formUsuario"));
    }

    @Test
    @DisplayName("debería guardar usuario y redirigir")
    @WithMockUser(roles = GERENTE)
    void guardar() throws Exception {
        String uri = RESOURCE_PATH + "/save";
        when(encoder.encode("password123")).thenReturn("hashed123");

        mockMvc.perform(post(uri)
                        .param("cuenta", "alex")
                        .param("nombre", "Alex Rivera")
                        .param("email", "alex@mail.com")
                        .param("pwd", "password123")
                        .param("perfil", "EDITOR")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios/index"));

        verify(serviceUsuario).guardar(any(Usuario.class));
        verify(servicePerfil).guardar(any(Perfil.class));
    }

    @Test
    @DisplayName("debería mostrar lista de usuarios")
    @WithMockUser(roles = GERENTE)
    void index_deberiaMostrarLista() throws Exception {
        String uri = RESOURCE_PATH + "/index";
        when(serviceUsuario.buscarTodos()).thenReturn(List.of());

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(view().name("usuarios/listUsuarios"))
                .andExpect(model().attributeExists("usuarios"));
    }



    @Test
    @DisplayName("debería eliminar usuario y redirigir")
    @WithMockUser(roles = GERENTE)
    void eliminar_deberiaEliminarUsuario() throws Exception {
        String uri = RESOURCE_PATH + "/delete/" + 1;
        mockMvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios/index"))
                .andExpect(flash().attributeExists("msg"));

        verify(servicePerfil).eliminarPorId(1);
        verify(serviceUsuario).eliminarPorId(1);
    }
}