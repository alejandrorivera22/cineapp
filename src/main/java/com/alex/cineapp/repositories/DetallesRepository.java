package com.alex.cineapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.cineapp.models.Detalle;

public interface DetallesRepository extends JpaRepository<Detalle, Integer> {
    
}
