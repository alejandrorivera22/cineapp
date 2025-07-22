package com.alex.cineapp.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alex.cineapp.models.Horario;

public interface IHorariosService {
    List<Horario> buscarPorIdPelicula(int id, Date fecha);
	void insertar(Horario horario);
	List<Horario> buscarTodos();
	Page<Horario> buscarTodos(Pageable page);
	void eliminarPorId(int idHorario);
	Horario buscarPorId(int idHorario);
} 
