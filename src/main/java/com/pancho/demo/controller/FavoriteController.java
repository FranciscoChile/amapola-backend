package com.pancho.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.pancho.demo.model.APIResponse;
import com.pancho.demo.model.FavDTO;
import com.pancho.demo.service.FavoriteService;



@RestController
@RequestMapping("/api/favs")
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<APIResponse> findAll() {

        List<FavDTO> list = favoriteService.findAll();

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(list);
        apiResponse.setResponseCode(HttpStatus.OK);
        apiResponse.setMessage("Successfully retrieved data");

        return new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<APIResponse> create(@RequestBody FavDTO favDTO) {
        try {
            favoriteService.create(favDTO) ;

            APIResponse apiResponse = new APIResponse();
            apiResponse.setData(favDTO);
            apiResponse.setResponseCode(HttpStatus.OK);
            apiResponse.setMessage("Successfully created");

            return new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> delete(@PathVariable String id) {
        try {
            favoriteService.delete(Long.parseLong(id));

            APIResponse apiResponse = new APIResponse();
            apiResponse.setResponseCode(HttpStatus.OK);
            apiResponse.setMessage("Successfully deleted");
            
            return new ResponseEntity<>(apiResponse, apiResponse.getResponseCode());

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found", e);
        }
    }
    

}
