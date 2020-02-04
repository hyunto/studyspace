package xyz.hyunto.oauth2provider.client;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class Application implements ClientDetails {

    private String clientId;

    private String clientSecret;

    private ClientType clientType;

    private Set<String> resourceIds = new HashSet<>();

    private Set<String> scope = new HashSet<>();

    private Set<String> webServerRedirectUri = new HashSet<>();

    private int accessTokenValidity;

    private Map<String, Object> additionalInformation = new HashMap<>();


    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        return this.resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return clientType == ClientType.CONFIDENTIAL;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return scope.size() > 0;
    }

    @Override
    public Set<String> getScope() {
        return this.scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        Set<String> grantTypes = new HashSet<>();
        grantTypes.add("authorization_code");
        grantTypes.add("refresh_token");
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.webServerRedirectUri;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.accessTokenValidity;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return null;
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.additionalInformation;
    }

    public void setName(String name) {
        this.additionalInformation.put("name", name);
    }

    public void setClientType(ClientType clientType) {
        additionalInformation.put("client_type", clientType.name());
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setAccessTokenValidity(int accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public void addRedirectUri(String redirectUri) {
        this.webServerRedirectUri.add(redirectUri);
    }

    public void addScope(String scope) {
        this.scope.add(scope);
    }

    public void addResourceId(String resourceId) {
        this.resourceIds.add(resourceId);
    }

}
