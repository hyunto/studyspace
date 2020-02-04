package xyz.hyunto.clientauthorizationcode.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.hyunto.clientauthorizationcode.user.ClientUser;
import xyz.hyunto.clientauthorizationcode.user.UserRepository;

import java.util.Optional;

public class ClientUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientUser> optionalUser = repository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("invalid username or password");
        }

        return new ClientUserDetails(optionalUser.get());
    }

}
