package xyz.hyunto.clientauthorizationcode.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import xyz.hyunto.clientauthorizationcode.security.ClientUserDetails;
import xyz.hyunto.clientauthorizationcode.user.ClientUser;
import xyz.hyunto.clientauthorizationcode.user.UserRepository;

import java.util.Calendar;

@Service
public class OAuth2ClientTokenServices implements ClientTokenServices {

    @Autowired
    private UserRepository userRepository;

    private ClientUser getClientUser(Authentication authentication) {
        ClientUserDetails loggedUser = (ClientUserDetails) authentication.getPrincipal();
        Long userId = loggedUser.getClientUser().getId();
        return userRepository.findOne(userId);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        ClientUser clientUser = this.getClientUser(authentication);

        String accessToken = clientUser.getAccessToken();
        Calendar expirationDate = clientUser.getAccessTokenValidity();
        if (accessToken == null)
            return null;

        DefaultOAuth2AccessToken oAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        oAuth2AccessToken.setExpiration(expirationDate.getTime());
        return oAuth2AccessToken;
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication, OAuth2AccessToken accessToken) {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime(accessToken.getExpiration());

        ClientUser clientUser = getClientUser(authentication);
        clientUser.setAccessToken(accessToken.getValue());
        clientUser.setAccessTokenValidity(expirationDate);

        userRepository.save(clientUser);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        ClientUser clientUser = getClientUser(authentication);
        clientUser.setAccessToken(null);
        clientUser.setRefreshToken(null);
        clientUser.setAccessTokenValidity(null);

        userRepository.save(clientUser);
    }
}
