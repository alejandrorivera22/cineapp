package com.alex.cineapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.cineapp.models.Noticia;

public interface NoticiasRepository extends JpaRepository<Noticia, Integer>{
    
    public List<Noticia> findTop3ByEstatusOrderByIdDesc(String estatus);

}
