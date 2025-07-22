package com.alex.cineapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.cineapp.models.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer>{
    
}
