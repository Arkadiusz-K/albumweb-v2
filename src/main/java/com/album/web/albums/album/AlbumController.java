package com.album.web.albums.album;

import com.album.web.albums.HibernateUtil;
import com.album.web.albums.album.Album;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/album")
public class AlbumController {

    @GetMapping
    public String showForm(Model model){
        return "album-form";
    }

    @GetMapping("/albums")
    public String getAllAlbums(){
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<Album> albums = session.createQuery("from Album", Album.class).list();
            albums.forEach(a -> System.out.println(a.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "album-form";
    }

    @GetMapping("getAlbum")
    public String getAlbum(@RequestParam("id") String id){
        long idOfAlbum;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            idOfAlbum = Long.parseLong(id);
            Album album = session.load(Album.class, idOfAlbum);
            System.out.println("------------ ALBUM ------------------");
            System.out.println("name: " + album.getName());
            System.out.println("author: " + album.getAuthor());
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
}
