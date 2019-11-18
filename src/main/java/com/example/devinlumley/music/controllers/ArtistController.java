package com.example.devinlumley.music.controllers;

import java.util.List;

import javax.validation.Valid;

import com.example.devinlumley.music.exceptions.ResourceNotFoundException;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/artists")
@Api(value = "Artist Controller", description = "Artist management")
public class ArtistController {

	@Autowired
	private ArtistRepository artistRepository;

	@GetMapping
	@ApiOperation(value = "View a list of all artists", response = Artist.class, responseContainer = "List")
	public List<Artist> getAllArtists() {
		return artistRepository.findAll();
	}

	@GetMapping("/{artistId}")
	@ApiOperation(value = "View an artist", response = Artist.class)
	public Artist getArtistById(@ApiParam(value = "Artist ID", required = true) @PathVariable("artistId") Long id) {
		return artistRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Artist " + id + " not found"));
	}

	@PostMapping
	@ApiOperation(value = "Create an artist", response = Artist.class)
	public Artist createArtist(@ApiParam(value = "Artist object", required = true) @Valid @RequestBody Artist artist) {
		return artistRepository.save(artist);
	}

	@PutMapping("/{artistId}")
	@ApiOperation(value = "Update an artist", response = Artist.class)
	public Artist updateArtist(@ApiParam(value = "Artist ID", required = true) @PathVariable("artistId") Long id,
			@ApiParam(value = "Artist object", required = true) @Valid @RequestBody Artist artistRequest) {
		return artistRepository.findById(id).map(artist -> {
			artist.setName(artistRequest.getName());
			return artistRepository.save(artist);
		}).orElseThrow(() -> new ResourceNotFoundException("Artist " + id + " not found"));
	}

	@DeleteMapping("/{artistId}")
	@ApiOperation(value = "Delete an artist")
	public ResponseEntity<?> deleteArtist(
			@ApiParam(value = "Artist ID", required = true) @PathVariable("artistId") Long id) {
		return artistRepository.findById(id).map(artist -> {
			artistRepository.delete(artist);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("Artist " + id + " not found"));
	}
}