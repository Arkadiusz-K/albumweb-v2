package com.album.web.albums.registration;

import com.album.web.albums.user.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;

    @RequestMapping(path = "/registration", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String registration(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }
}
