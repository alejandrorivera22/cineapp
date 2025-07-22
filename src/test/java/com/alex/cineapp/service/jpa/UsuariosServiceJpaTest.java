package com.alex.cineapp.service.jpa;

import com.alex.cineapp.models.Pelicula;
import com.alex.cineapp.models.Usuario;
import com.alex.cineapp.repositories.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuariosServiceJpaTest extends ServiceSpec{

    @Mock
    UsuariosRepository usuariosRepository;

    @InjectMocks
    UsuariosServiceJpa usuariosService;

    Usuario usuario;
    Usuario usuario2;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setCuenta("Usuario");
        usuario.setEmail("email");
        usuario.setPwd("Contraseña segura");

        usuario2 = new Usuario();
        usuario2.setId(2);
        usuario2.setCuenta("Usuario2");
        usuario2.setEmail("email2");
        usuario2.setPwd("Contraseña segura2");
    }

    @Test
    @DisplayName("Debería Guardar un usuario")
    void guardar_DeberiaInsertarPelicula() {
        when(usuariosRepository.findAll())
                .thenReturn(List.of(usuario))     // Primera llamada
                .thenReturn(List.of(usuario,usuario2)); // Segunda llamada

        assertEquals(1, usuariosService.buscarTodos().size());
        usuariosService.guardar(usuario2);
        assertEquals(2, usuariosService.buscarTodos().size());
    }

    @Test
    @DisplayName("Debería retornar todas los usuarios")
    void buscarTodos_DeberiaRetornarListaPeliculas() {
        when(usuariosRepository.findAll()).thenReturn(List.of(usuario, usuario2));
        List<Usuario> usuarios = usuariosService.buscarTodos();
        assertEquals(2, usuarios.size());
    }

    @Test
    @DisplayName("Debería retornar una pelicula por ID")
    void buscarPorId_DeberiaRetornarPelicula() {
        when(usuariosRepository.findById(1)).thenReturn(Optional.of(usuario));
        Usuario usuarioResultado = usuariosService.buscarPorId(1);
        assertNotNull(usuarioResultado);
        assertEquals(usuario.getCuenta() , usuarioResultado.getCuenta());
    }

    @Test
    void eliminarPorId() {
        when(usuariosRepository.findAll())
                .thenReturn(List.of(usuario, usuario2))
                .thenReturn(List.of(usuario));
        List<Usuario> usuariosAntes = usuariosService.buscarTodos();
        assertEquals(2, usuariosAntes.size());

        int id = usuariosAntes.get(0).getId();

        usuariosService.eliminarPorId(id);

        List<Usuario> usuariosDespues = usuariosService.buscarTodos();
        assertEquals(1, usuariosDespues.size());
        verify(usuariosRepository, times(1)).deleteById(anyInt());
    }
}