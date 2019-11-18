package com.example.devinlumley.music.controllers;

import java.util.List;

import javax.validation.Valid;

import com.example.devinlumley.music.expceptions.ResourceNotFoundException;
import com.example.devinlumley.music.models.Artist;
import com.example.devinlumley.music.repositories.ArtistRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artists")
public class ArtistController {

	@Autowired
	private ArtistRepository artistRepository;

	@GetMapping
	public List<Artist> getAllArtists() {
		return artistRepository.findAll();
	}

	@GetMapping("/{id}")
	public Artist getArtistById(@PathVariable("id") Long id) {
		return artistRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Artist " + id + " not found"));
	}

	@PostMapping
	public Artist createArtist(@Valid @RequestBody Artist artist) {
		return artistRepository.save(artist);
	}

	@PutMapping("/{id}")
	public Artist updateArtist(@PathVariable("id") Long id, @Valid @RequestBody Artist artistRequest) {
		return artistRepository.findById(id).map(artist -> {
			artist.setName(artistRequest.getName());
			return artistRepository.save(artist);
		}).orElseThrow(() -> new ResourceNotFoundException("Artist " + id + " not found"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteArtist(@PathVariable("id") Long id) {
		return artistRepository.findById(id).map(artist -> {
			artistRepository.delete(artist);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Artist " + id + " not found"));
	}
}