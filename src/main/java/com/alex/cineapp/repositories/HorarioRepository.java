package com.alex.cineapp.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alex.cineapp.models.Horario;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer>{
    
     List<Horario> findByPelicula_IdAndFechaOrderByHora(int id, Date fecha);


    @Query("select h from Horario h where h.fecha = :fecha and pelicula.estatus='Activa' group by h.pelicula order by pelicula.id asc")
     List<Horario> findByFecha(@Param("fecha") Date fecha);
}
