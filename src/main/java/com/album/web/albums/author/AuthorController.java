package com.album.web.albums.author;

import com.album.web.albums.HibernateUtil;
import com.album.web.albums.album.Album;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/author")
public class AuthorController {

    @GetMapping
    public String showForm(Model model){
        return "author-form";
    }

    @PostMapping
    public String addAlbum(@ModelAttribute("author") Author author){
        System.out.println(author.getName());
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(author);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return "author-form";
    }

    @GetMapping("{id}")
    public String getAuthor(@PathVariable("id") Long authorId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Author author = session.load(Author.class, authorId);
            System.out.println("------------ AUTHOR ------------------");
            System.out.println("name: " + author.getName());
            System.out.println("id: " + author.getId());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "author-form";
    }

    @GetMapping("/authors")
    public String getAllAuthors(){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Author> authors = session.createQuery("from Author", Author.class).list();
            authors.forEach(a -> System.out.println("Author: " + a.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "album-form";
    }

    @GetMapping("/{id}/albums")
    public String getAllAlbumsForOneAuthor(@PathVariable("id") Long authorId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Author author = session.load(Author.class, authorId);
            System.out.println("------------ ALL ALBUMS FOR AUTHOR: " + author.getName());
            for(Album album : author.getAlbums()){
                System.out.println("Album's name: " + album.getName());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return "author-form";
    }
}
