package com.album.web.albums.author;

import com.album.web.albums.album.Album;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "author")
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Id cannot be null")
    @Column(name = "id")
    private long id;
    @NotNull(message = "Name cannot be null")
    @Column(name = "authorName", unique = true)
    private String name;
    @OneToMany
    @JoinTable(
            name = "albums_and_authors",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    @Column(name = "albums")
    private Set<Album> albums;
}
