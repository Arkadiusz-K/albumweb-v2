package com.album.web.albums;

import com.album.web.albums.album.Album;
import com.album.web.albums.author.Author;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AuthorAlbumController {

    // PUT method will be better, but GET is more comfortable to tests
    @GetMapping("/al/{albumId}/au/{authorId}")
    public String assignAlbumToAuthor(
            @PathVariable Long albumId,
            @PathVariable Long authorId
    ){
        System.out.println("------- from AuthorAlbum Controller -------");
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Album album = session.get(Album.class, albumId);
            Author author = session.get(Author.class, authorId);
            author.getAlbums().add(album);
            album.setAuthor(author);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return "album-form";
    }
}
