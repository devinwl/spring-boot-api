package com.example.devinlumley.music.controllers;

import java.util.List;

import javax.validation.Valid;

import com.example.devinlumley.music.expceptions.ResourceNotFoundException;
import com.example.devinlumley.music.models.Album;
import com.example.devinlumley.music.repositories.AlbumRepository;
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
@RequestMapping("/artists/{artistId}/albums")
public class AlbumController {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ArtistRepository artistRepository;

	@GetMapping
	public List<Album> getAllAbums() {
		return albumRepository.findAll();
	}

	@PostMapping
	public Album createAlbum(@PathVariable("artistId") Long artistId, @Valid @RequestBody Album album) {
		return artistRepository.findById(artistId).map(artist -> {
			album.setArtist(artist);
			return albumRepository.save(album);
		}).orElseThrow(() -> new ResourceNotFoundException("Artist " + artistId + " not found."));
	}

	@PutMapping("/{id}")
	public Album updateAlbum(@PathVariable("artistId") Long artistId, @PathVariable("id") Long albumId,
			@Valid @RequestBody Album requestAlbum) {
		if (!artistRepository.existsById(artistId)) {
			throw new ResourceNotFoundException("Artist " + artistId + " not found");
		}

		return albumRepository.findById(albumId).map(album -> {
			album.setName(requestAlbum.getName());
			return albumRepository.save(album);
		}).orElseThrow(() -> new ResourceNotFoundException("Album " + albumId + " not found"));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAlbum(@PathVariable("artistId") Long artistId, @PathVariable("id") Long albumId) {
		return albumRepository.findByIdAndArtistId(albumId, artistId).map(album -> {
			albumRepository.delete(album);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException(
				"Album not found with id " + albumId + " and Artist id " + artistId));
	}
}