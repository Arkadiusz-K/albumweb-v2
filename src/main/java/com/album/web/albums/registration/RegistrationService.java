package com.album.web.albums.registration;

import com.album.web.albums.user.AppUserService;
import com.album.web.albums.user.AppUser;
import com.album.web.albums.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private EmailValidator emailValidator;
    private AppUserService appUserService;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail){
            throw new IllegalStateException("email not valid");
        }
        return appUserService.singUpUser(
                new AppUser(
                      request.getName(),
                      request.getLastName(),
                      request.getEmail(),
                      request.getPassword(),
                      UserRole.USER
                )
        );
    }
}
