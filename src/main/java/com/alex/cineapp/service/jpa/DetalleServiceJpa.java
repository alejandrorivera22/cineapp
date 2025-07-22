package com.alex.cineapp.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex.cineapp.models.Detalle;
import com.alex.cineapp.repositories.DetallesRepository;
import com.alex.cineapp.service.IDetalleService;

@Service
public class DetalleServiceJpa implements IDetalleService {

    @Autowired
    private DetallesRepository detallesRepository;

    @Override
    public void insertar(Detalle detalle) {
      detallesRepository.save(detalle);
    }

    @Override
    public void eliminar(int id) {
     detallesRepository.deleteById(id);
    }
    
}
