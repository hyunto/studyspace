package xyz.hyunto.oauth2provider.client;

import lombok.Data;

@Data
public class BasicClientInfo {

    private String name;

    private String redirectUri;

    private String clientType;

}
