package com.alex.cineapp.service;

import com.alex.cineapp.models.Detalle;

public interface IDetalleService {
    void insertar(Detalle detalle);
    void eliminar(int id);
}
