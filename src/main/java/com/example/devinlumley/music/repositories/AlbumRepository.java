package com.example.devinlumley.music.repositories;

import java.util.Optional;

import com.example.devinlumley.music.models.Album;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
	Page<Album> findByArtistId(Long artistId, Pageable pageable);

	Optional<Album> findByIdAndArtistId(Long id, Long artistId);
}