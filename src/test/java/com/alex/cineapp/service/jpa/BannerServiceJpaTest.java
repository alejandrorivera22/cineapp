package com.alex.cineapp.service.jpa;

import com.alex.cineapp.models.Banner;
import com.alex.cineapp.repositories.BannerRepository;
import com.alex.cineapp.service.IBannerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BannerServiceJpaTest extends ServiceSpec{

    @Mock
    private BannerRepository bannerRepository;

    @InjectMocks
    private BannerServiceJpa bannerService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Banner b1;
    Banner b2;
    Banner b3;

    @BeforeEach
    void setUp() throws Exception {
        bannerRepository.deleteAll(); // Limpiar antes de cada test

        b1 = new Banner();
        b1.setId(1);
        b1.setTitulo("Test Banner 1");
        b1.setArchivo("banner1.jpg");
        b1.setEstatus("Activo");
        b1.setFecha(sdf.parse("2024-07-20"));

        b2 = new Banner();
        b2.setId(2);
        b2.setTitulo("Test Banner 2");
        b2.setArchivo("banner2.jpg");
        b2.setEstatus("Inactivo");
        b2.setFecha(sdf.parse("2024-07-21"));

         b3 = new Banner();
         b3.setId(3);
        b3.setTitulo("Test Banner 3");
        b3.setArchivo("banner3.jpg");
        b3.setEstatus("Activo");
        b3.setFecha(sdf.parse("2024-07-22"));


    }

    @Test
    @DisplayName("Debería insertar un banner")
    void insertar_DeberiaGuardarBanner() {
        when(bannerRepository.findAll())
                .thenReturn(List.of(b1, b2))     // Primera llamada
                .thenReturn(List.of(b1, b2, b3)); // Segunda llamada

        assertEquals(2, bannerService.buscarTodos().size());
        bannerService.insertar(b3);
        assertEquals(3, bannerService.buscarTodos().size());
    }

    @Test
    @DisplayName("Debería retornar todos los banners")
    void buscarTodos_DeberiaRetornarListaBanners() {
        when(bannerRepository.findAll()).thenReturn(List.of(b1, b2));
        List<Banner> banners = bannerService.buscarTodos();
        assertEquals(2, banners.size());
    }

    @Test
    @DisplayName("Debería retornar un banner por ID")
    void buscarPorId_DeberiaRetornarBAnner() {
        when(bannerRepository.findById(1)).thenReturn(Optional.of(b1));
        Banner bannerr = bannerService.buscarPorId(1);
        assertNotNull(bannerr);
        assertEquals("Test Banner 1", bannerr.getTitulo());
    }

    @Test
    @DisplayName("Debería eliminar un banner por ID")
    void eliminarPorId() {
        when(bannerRepository.findAll())
                .thenReturn(List.of(b1, b3))
                .thenReturn(List.of(b3));
        List<Banner> bannersAntes = bannerService.buscarTodos();
        assertEquals(2, bannersAntes.size());

        int id = bannersAntes.get(0).getId();

        bannerService.eliminarPorId(id);

        List<Banner> bannersDespues = bannerService.buscarTodos();
        assertEquals(1, bannersDespues.size());
       verify(bannerRepository, times(1)).deleteById(anyInt());
    }
}