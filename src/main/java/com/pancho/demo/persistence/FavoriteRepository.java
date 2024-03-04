package com.pancho.demo.persistence;

import org.springframework.data.repository.CrudRepository;

import com.pancho.demo.model.FavEntity;

public interface FavoriteRepository extends CrudRepository<FavEntity, Long> {
    
}
