package com.alex.cineapp.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.alex.cineapp.models.Banner;
import com.alex.cineapp.repositories.BannerRepository;
import com.alex.cineapp.service.IBannerService;

@Service
@Primary
public class BannerServiceJpa implements IBannerService{

    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public void insertar(Banner banner) {
        bannerRepository.save(banner);
    }

    @Override
    public List<Banner> buscarTodos() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner buscarPorId(int id) {
       Optional<Banner> bannerDb = bannerRepository.findById(id);
       if (bannerDb.isPresent()) {
            return bannerDb.get();
       }
       return bannerDb.orElseThrow();
    }

    @Override
    public void eliminarPorId(int id) {
        bannerRepository.deleteById(id);
    }
    
}
