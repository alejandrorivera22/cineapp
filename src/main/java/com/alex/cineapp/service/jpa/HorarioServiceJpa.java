package com.alex.cineapp.service.jpa;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alex.cineapp.models.Horario;
import com.alex.cineapp.repositories.HorarioRepository;
import com.alex.cineapp.service.IHorariosService;

@Service
public class HorarioServiceJpa implements IHorariosService{

    @Autowired
    private  HorarioRepository horarioRepository;

    @Override
    public List<Horario> buscarPorIdPelicula(int id, Date fecha) {

       return horarioRepository.findByPelicula_IdAndFechaOrderByHora(id, fecha);
    }

    @Override
    public void insertar(Horario horario) {
        horarioRepository.save(horario);
    }

    @Override
    public List<Horario> buscarTodos() {
       return horarioRepository.findAll();
    }

    @Override
    public Page<Horario> buscarTodos(Pageable page) {
       return horarioRepository.findAll(page);
    }

    @Override
    public void eliminarPorId(int idHorario) {
		horarioRepository.deleteById(idHorario);
    }

    @Override
    public Horario buscarPorId(int idHorario) {
        Optional<Horario> optional = horarioRepository.findById(idHorario);
		if (optional.isPresent())
			return optional.get();
		return null;
    }
    
}
