package com.alex.cineapp.service.jpa;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alex.cineapp.models.Horario;
import com.alex.cineapp.models.Pelicula;
import com.alex.cineapp.repositories.HorarioRepository;
import com.alex.cineapp.repositories.PeliculaRepository;
import com.alex.cineapp.service.IPeliculaService;

@Service
public class PelisculaServiceJpa implements IPeliculaService{

    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private HorarioRepository horariosRepo;

    @Override
    public void insertar(Pelicula pelicula) {
        peliculaRepository.save(pelicula);
    }

    @Override
    public List<Pelicula> buscarTodas() {
       return peliculaRepository.findAll();
    }

    @Override
    public Pelicula buscarPorId(int idPelicula) {
        Optional<Pelicula> pelicula = peliculaRepository.findById(idPelicula);
        if (pelicula.isPresent()) {
            return pelicula.get();
        }
        return pelicula.orElseThrow();

    }

    @Override
    public List<String> buscarGeneros() {
         List<String> generos = new LinkedList<>();
        generos.add("Accion");
        generos.add("Aventuras");
        generos.add("Clasicas");
        generos.add("Drama");
        generos.add("Comedia");
        generos.add("Terror");
        generos.add("Infantil");
        generos.add("Accion y Aventura");
        generos.add("Romantica");
        generos.add("Ciencia ficcion");

        return generos;
    }

    @Override
    public void eliminar(int id) {
       peliculaRepository.deleteById(id);
    }

    @Override
    public Page<Pelicula> buscarTodas(Pageable pageable) {
       return peliculaRepository.findAll(pageable);
    }

    @Override
    public List<Pelicula> buscarActiva() {
        return peliculaRepository.findByEstatus_OrderByTitulo("Activa");
    }

    @Override
    public List<Pelicula> buscarPorFecha(Date fecha) {
        List<Pelicula> peliculas = null;
		// Buscamos en la tabla de horarios, [agrupando por idPelicula]
		List<Horario> horarios = horariosRepo.findByFecha(fecha);
		peliculas = new LinkedList<>();

		// Formamos la lista final de Peliculas que regresaremos.
		for (Horario h : horarios) {
			// Solo nos interesa de cada registro de horario, el registro de pelicula.
			peliculas.add(h.getPelicula());
		}		
		return peliculas;
    }
    
}
