package com.alex.cineapp.service;

import com.alex.cineapp.models.Perfil;

public interface IPerfilesService {
    void guardar(Perfil perfil);
    Perfil buscarPorId(int id);
    void eliminarPorId(int id);

}
