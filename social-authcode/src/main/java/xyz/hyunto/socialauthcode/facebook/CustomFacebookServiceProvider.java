package xyz.hyunto.socialauthcode.facebook;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Template;

public class CustomFacebookServiceProvider extends AbstractOAuth2ServiceProvider<Facebook> {

	private String appNamespace;

	private String apiVersion;

	public CustomFacebookServiceProvider(String appId, String appSecret, String apiVersion) {
		super(getOAuth2Template(appId, appSecret, apiVersion));
		this.apiVersion = apiVersion;
	}

	private static OAuth2Template getOAuth2Template(String appId, String appSecret, String apiVersion) {
		String graphApiUrl = "https://graph.facebook.com/v" + apiVersion + "/";
		String authorizationUrl = "https://facebook.com/v" + apiVersion + "/dialog/oauth";
		OAuth2Template template = new OAuth2Template(appId, appSecret, authorizationUrl, graphApiUrl + "oauth/access_token");
		template.setUseParametersForClientAuthentication(true);
		return template;
	}

	@Override
	public Facebook getApi(String accessToken) {
		FacebookTemplate template = new FacebookTemplate(accessToken, appNamespace);
		template.setApiVersion(apiVersion);
		return template ;
	}

}
