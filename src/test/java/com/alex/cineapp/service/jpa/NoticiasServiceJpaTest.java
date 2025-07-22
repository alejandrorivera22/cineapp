package com.alex.cineapp.service.jpa;

import com.alex.cineapp.models.Banner;
import com.alex.cineapp.models.Noticia;
import com.alex.cineapp.repositories.NoticiasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoticiasServiceJpaTest extends ServiceSpec{

    @Mock
    NoticiasRepository noticiasRepository;

    @InjectMocks
    NoticiasServiceJpa noticiasServiceJpa;

    Noticia noticia1;
    Noticia noticia2;
    Noticia noticia3;

    @BeforeEach
    void setUp() {
        noticia1 = new Noticia();
        noticia1.setId(1);
        noticia1.setDetalle("Detalle1");
        noticia1.setEstatus("Activa");
        noticia1.setTitulo("Noticia1");

        noticia2 = new Noticia();
        noticia2.setId(2);
        noticia2.setDetalle("Detalle2");
        noticia2.setEstatus("Activa");
        noticia2.setTitulo("Noticia2");

        noticia3 = new Noticia();
        noticia3.setId(3);
        noticia3.setDetalle("Detalle3");
        noticia3.setEstatus("Activa");
        noticia3.setTitulo("Noticia3");
    }

    @Test
    @DisplayName("Debería Guardar una noticia")
    void guardar_DeberiaGuardarNoticia() {
        when(noticiasRepository.findAll())
                .thenReturn(List.of(noticia1))     // Primera llamada
                .thenReturn(List.of(noticia1,noticia2)); // Segunda llamada

        assertEquals(1, noticiasServiceJpa.buscarTodas().size());
        noticiasServiceJpa.guardar(noticia2);
        assertEquals(2, noticiasServiceJpa.buscarTodas().size());
    }

    @Test
    @DisplayName("Debería regresar las tres ultimas noticias con estatus activo")
    void buscarUltimas_DeberiaRetornarTresUltimasNoticias() {
        List<Noticia> noticiasEsperadas = List.of(noticia1, noticia2, noticia2);
        when(noticiasRepository.findTop3ByEstatusOrderByIdDesc("Activa"))
                .thenReturn(List.of(noticia1, noticia2, noticia3));

        List<Noticia> noticiasResultado = noticiasServiceJpa.buscarUltimas();
        assertNotNull(noticiasResultado);

        assertEquals(noticiasEsperadas.size(), noticiasResultado.size());
        assertEquals(noticiasEsperadas.get(0).getEstatus(), noticiasResultado.get(0).getEstatus());
        assertEquals(noticiasEsperadas.get(1).getEstatus(), noticiasResultado.get(1).getEstatus());
        assertEquals(noticiasEsperadas.get(2).getEstatus(), noticiasResultado.get(2).getEstatus());
    }

    @Test
    @DisplayName("Debería regresar todas las noticias")
    void buscarTodas_DeberiaRetornarListaHorarios() {
        when(noticiasRepository.findAll()).thenReturn(List.of(noticia1, noticia2, noticia3));
        List<Noticia> noticias = noticiasServiceJpa.buscarTodas();
        assertEquals(3, noticias.size());
    }

    @Test
    @DisplayName("Debería retornar una noticia por ID")
    void buscarPorId_DeberiaRetornarNoticia() {
        when(noticiasRepository.findById(1)).thenReturn(Optional.of(noticia1));
        Noticia noticiaResultado = noticiasServiceJpa.buscarPorId(1);
        assertNotNull(noticiaResultado);
        assertEquals(noticia1.getTitulo() , noticiaResultado.getTitulo());
    }

    @Test
    void eliminar() {
        when(noticiasRepository.findAll())
                .thenReturn(List.of(noticia1, noticia2))
                .thenReturn(List.of(noticia1));
        List<Noticia> noticiasAntes = noticiasServiceJpa.buscarTodas();
        assertEquals(2, noticiasAntes.size());

        int id = noticiasAntes.get(0).getId();

        noticiasServiceJpa.eliminar(id);

        List<Noticia> noticiasDespues = noticiasServiceJpa.buscarTodas();
        assertEquals(1, noticiasDespues.size());
        verify(noticiasRepository, times(1)).deleteById(anyInt());
    }
}