package com.example.bai2.controller;

import com.example.bai2.model.entity.Artwork;
import com.example.bai2.service.ArtworkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gallery/artworks")
public class ArtworkController {
    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @GetMapping
    public ResponseEntity<List<Artwork>> getAllArtworks() {
        return new ResponseEntity<>(artworkService.getAllArtworks(), HttpStatus.OK);
    }
}
