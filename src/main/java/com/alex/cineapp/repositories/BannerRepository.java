package com.alex.cineapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alex.cineapp.models.Banner;

public interface BannerRepository extends JpaRepository<Banner, Integer>{
    
}
