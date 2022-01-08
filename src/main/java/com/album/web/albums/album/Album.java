package com.album.web.albums.album;

import com.album.web.albums.author.Author;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Id cannot be null")
    @Column(name = "id")
    private long albumId;
    @NotNull(message = "Name cannot be null")
    @Column(name = "name", unique = true)
    private String name;
    @NotNull(message = "Author cannot be null")
    @OneToOne
    private Author author;
    @Column(name = "releaseDate")
    private String releaseDate;
    @Column(name="playlist")
    private String playlist;
    @Column(name="description")
    private String description;
    @Column(name = "numberOfVotes")
    private long numberOfVotes;
    @Column(name = "sumOfVotes")
    private double sumOfVotes;
}
