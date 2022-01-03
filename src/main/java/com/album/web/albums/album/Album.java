package com.album.web.albums.album;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name="album")
public class Album {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @NotNull(message = "Id cannot be null")
    @Column(name="id")
    private long id;
    @NotNull(message = "Name cannot be null")
    @Column(name="name")
    private String name;
    @NotNull(message = "Author cannot be null")
    @Column(name="author")
    private String author;
    @Column(name="releaseDate")
    private String releaseDate;
    @Column(name="playlist")
    private String playlist;
    @Column(name="description")
    private String description;
    @Column(name="numberOfVotes")
    private long numberOfVotes;
    @Column(name="sumOfVotes")
    private double sumOfVotes;
}
