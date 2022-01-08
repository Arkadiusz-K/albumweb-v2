package com.album.web.albums.album;

import com.album.web.albums.HibernateUtil;

import javax.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AlbumSearchController {

    @GetMapping("/search")
    public String search(@RequestParam(value = "search", required = false) String search, Model model){
        System.out.println("SEARCH MAPPING: \n-----------------");
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query<Album> query = session.createQuery("from Album a where a.name=:name");
            query.setParameter("name", search);
            Album album = query.getSingleResult();
            System.out.println("Album name: " + album.getName() + ", author's name: " + album.getAuthor().getName());
            model.addAttribute("searchedAlbum",album.getName());
        } catch (NoResultException e) {
            System.out.println("Result not found");
        } catch (Exception e){
            e.printStackTrace();
        }
        return "home";
    }
}