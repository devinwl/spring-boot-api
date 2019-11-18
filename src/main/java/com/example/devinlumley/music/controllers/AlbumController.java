package com.example.devinlumley.music.controllers;

import java.util.List;

import javax.validation.Valid;

import com.example.devinlumley.music.exceptions.ResourceNotFoundException;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/artists/{artistId}/albums")
@Api(value = "Album Controller", description = "Album management")
public class AlbumController {

	@Autowired
	private AlbumRepository albumRepository;

	@Autowired
	private ArtistRepository artistRepository;

	@GetMapping
	@ApiOperation(value = "View a list of all albums belonging to an artist", response = Album.class, responseContainer = "List")
	public List<Album> getAllAlbums(
			@ApiParam(value = "Artist ID", required = true) @PathVariable("artistId") Long artistId) {
		return albumRepository.findByArtistId(artistId);
	}

	@PostMapping
	@ApiOperation(value = "Create an album", response = Album.class)
	public Album createAlbum(@ApiParam(value = "Artist ID", required = true) @PathVariable("artistId") Long artistId,
			@ApiParam(value = "Album object", required = true) @Valid @RequestBody Album album) {
		return artistRepository.findById(artistId).map(artist -> {
			album.setArtist(artist);
			return albumRepository.save(album);
		}).orElseThrow(() -> new ResourceNotFoundException("Artist " + artistId + " not found."));
	}

	@PutMapping("/{albumId}")
	@ApiOperation(value = "Update an album", response = Album.class)
	public Album updateAlbum(@ApiParam(value = "Artist ID", required = true) @PathVariable("artistId") Long artistId,
			@ApiParam(value = "Album ID", required = true) @PathVariable("albumId") Long albumId,
			@ApiParam(value = "Album object", required = true) @Valid @RequestBody Album requestAlbum) {
		if (!artistRepository.existsById(artistId)) {
			throw new ResourceNotFoundException("Artist " + artistId + " not found");
		}

		return albumRepository.findById(albumId).map(album -> {
			album.setName(requestAlbum.getName());
			return albumRepository.save(album);
		}).orElseThrow(() -> new ResourceNotFoundException("Album " + albumId + " not found"));
	}

	@DeleteMapping("/{albumId}")
	@ApiOperation(value = "Delete an album")
	public ResponseEntity<?> deleteAlbum(
			@ApiParam(value = "Artist ID", required = true) @PathVariable("artistId") Long artistId,
			@ApiParam(value = "Album ID", required = true) @PathVariable("albumId") Long albumId) {
		return albumRepository.findByIdAndArtistId(albumId, artistId).map(album -> {
			albumRepository.delete(album);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException(
				"Album not found with id " + albumId + " and Artist id " + artistId));
	}
}