package com.pancho.demo.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pancho.demo.model.FavDTO;
import com.pancho.demo.model.FavEntity;
import com.pancho.demo.persistence.FavoriteRepository;

@Service
public class FavoriteService {
    
    @Autowired
    private FavoriteRepository favoriteRepository;

        public List<FavDTO> findAll() {

        List<FavDTO> listDTO = new ArrayList<>();

        Iterable<FavEntity> list = favoriteRepository.findAll();

        for (FavEntity task : list) {            
            FavDTO favDTO  = new FavDTO();
            favDTO.setId(task.getId());
            favDTO.setTitle(task.getTitle());
            favDTO.setDescription(task.getDescription());
            favDTO.setSummary(task.getSummary());
            favDTO.setImage_url(task.getImageUrl());
            favDTO.setPublished_at(task.getPublishedAt());
            listDTO.add(favDTO);
        }

        return listDTO;
    }


    public FavDTO create(FavDTO favDTO) {

        FavEntity favoriteEntity = FavEntity.builder()
        .id(favDTO.getId())
        .title(favDTO.getTitle())
        .description(favDTO.getDescription())
        .summary(favDTO.getSummary())
        .imageUrl(favDTO.getImage_url())
        .publishedAt(new Timestamp(favDTO.getPublished_at().getTime()))
        .favorite(true)        
        .build();

        favoriteRepository.save(favoriteEntity);
        
        return favDTO;    
    }

    public void delete (Long id) {
        favoriteRepository.deleteById(id);
    }
}
