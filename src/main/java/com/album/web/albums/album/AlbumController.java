package com.album.web.albums.album;

import com.album.web.albums.HibernateUtil;
import com.album.web.albums.vote.Vote;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    AlbumRepository albumRepository;

    @GetMapping
    public String showForm(){
        return "album-form";
    }

    @GetMapping("/albums")
    public String getAllAlbums(){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Album> albums = session.createQuery("from Album", Album.class).list();
            albums.forEach(a -> System.out.println("Album name: " + a.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "album-form";
    }

    @GetMapping("{id}")
    public String getAlbum(@PathVariable("id") Long albumId){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Album album = session.load(Album.class, albumId);
            System.out.println("------------ ALBUM ------------------");
            System.out.println("name: " + album.getName());
            System.out.println("author: " + album.getAuthor().getName());
        } catch (Exception e){
            e.printStackTrace();
        }
        return "album-form";
    }

    @PostMapping
    public String addAlbum(@ModelAttribute("album") Album album){
        System.out.println(album.getName());
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(album);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return "album-added";
    }

    @DeleteMapping
    public String deleteAlbum(@RequestParam("id") String id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        long idOfAlbum;
        try{
            idOfAlbum = Long.parseLong(id);
            Album album = session.load(Album.class, idOfAlbum);
            System.out.println(album.getName());
            transaction = session.beginTransaction();
            session.delete(album);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return "album-form";
    }

    @PutMapping
    public String updateAlbum(@ModelAttribute("album") Album album){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.update(album);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return "album-form";
    }

    @GetMapping("/thedarksideofthemoon")
    public String setAlbumPage(){
        return "album";
    }

    @PostMapping("/rateAlbum")
    public String rateAlbum(@ModelAttribute("vote") Vote vote){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(" RATE ALBUM CONTROLLER -- &&&&&&&&&&&&&&&&& VOTE &&&&&&&&&&&&&&&&&");
        vote.setVotingUser(auth.getName());

        System.out.println("Albums name: " + vote.getAlbumName());
        System.out.println("Username: " + vote.getVotingUser());
        System.out.println("Vote: " + vote.getVote());
        System.out.println("Id: " + vote.getVoteId());

        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();

            Query<Album> query = session.createQuery("from Album a where a.name=:name", Album.class);
            query.setParameter("name", vote.getAlbumName());
            Album album = query.getSingleResult();

            vote.setAlbum(album);
            session.save(vote);
            transaction.commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return "home";
    }
}
