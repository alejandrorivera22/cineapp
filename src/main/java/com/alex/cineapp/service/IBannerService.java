package com.alex.cineapp.service;

import com.alex.cineapp.models.Banner;

import java.util.List;

public interface IBannerService {
    void insertar(Banner banner);
    List<Banner> buscarTodos();
    Banner buscarPorId(int id);
    void eliminarPorId(int id);
}
