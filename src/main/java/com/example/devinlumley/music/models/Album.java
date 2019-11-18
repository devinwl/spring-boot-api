package com.example.devinlumley.music.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Album {
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Size(max = 255)
	@Column(unique = true)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "artist_id", nullable = false)
	@JsonIgnore
	private Artist artist;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}