package com.alex.cineapp.service.jpa;

import com.alex.cineapp.models.Perfil;
import com.alex.cineapp.repositories.PerfilesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerfilServiceJpaTest extends ServiceSpec{

    @Mock
    PerfilesRepository perfilesRepository;
    
    @InjectMocks
    PerfilServiceJpa perfilService;
    
    Perfil editor;
    Perfil gerente;
    Perfil nuevo;

    @BeforeEach
    void setUp() {
        editor = new Perfil();
        editor.setId(1);
        editor.setPerfil("EDITOR");
        editor.setCuenta("luis");

        gerente = new Perfil();
        gerente.setId(2);
        gerente.setPerfil("GERENTE");
        gerente.setCuenta("marisol");

        nuevo = new Perfil();
        nuevo.setId(3);
        nuevo.setPerfil("NUEVO");
        nuevo.setCuenta("Dan");
    }

    @Test
    @DisplayName("Debería Guardar un Perfil")
    void guardar_DeberiaGuardarPelicula() {
        perfilService.guardar(nuevo);
        verify(perfilesRepository).save(nuevo);
    }

    @Test
    @DisplayName("Debería retornar un perfil por ID")
    void buscarPorId_DeberiaRetornarPerfil() {
        when(perfilesRepository.findById(1)).thenReturn(Optional.of(editor));

        Perfil resultado = perfilService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(editor.getId(), resultado.getId());
        assertEquals(editor.getCuenta(), resultado.getCuenta());
    }

    @Test
    @DisplayName("Debería eliminar un perfil por ID")
    void eliminarPorId__DeberiaEliminarPerfil() {
        perfilService.eliminarPorId(1);
        verify(perfilesRepository).deleteById(1);
    }

    @Test
    @DisplayName("Debería lanzar excepción si el perfil no existe")
    void buscarPorId_PerfilNoExiste_DeberiaLanzarExcepcion() {
        when(perfilesRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> perfilService.buscarPorId(1));
    }
}