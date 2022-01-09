package com.album.web.albums.vote;

import com.album.web.albums.album.Album;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "Id cannot be null")
    @Column(name = "vote_id")
    private long voteId;
    @OneToOne
    private Album album;
    @Column(name = "album_name")
    private String albumName;
    @Column(name = "voting_user")
    private String votingUser;
    @Column(name = "vote")
    private int vote;
}
