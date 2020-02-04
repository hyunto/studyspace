package xyz.hyunto.passwordserver.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/api/profile")
    public ResponseEntity<UserProfile> profile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername() + "@mailinator.com";

        UserProfile profile = new UserProfile();
        profile.setName(user.getUsername());
        profile.setEmail(email);

        return ResponseEntity.ok(profile);
    }
}