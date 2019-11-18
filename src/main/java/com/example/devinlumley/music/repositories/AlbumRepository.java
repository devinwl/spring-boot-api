package com.example.devinlumley.music.repositories;

import java.util.List;
import java.util.Optional;

import com.example.devinlumley.music.models.Album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
	List<Album> findByArtistId(Long artistId);

	Optional<Album> findByIdAndArtistId(Long id, Long artistId);
}