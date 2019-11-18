package com.example.devinlumley.music.repositories;

import com.example.devinlumley.music.models.Artist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}