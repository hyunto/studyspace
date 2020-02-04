package xyz.hyunto.socialauthcode.facebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.social.FacebookProperties;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.api.Facebook;

@Configuration
@EnableSocial
@EnableConfigurationProperties(FacebookProperties.class)
public class FacebookConfiguration extends SocialAutoConfigurerAdapter {

	@Autowired
	private EnhancedFacebookProperties properties;

	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		return new CustomFacebookConnectionFactory(this.properties.getAppId(),
				this.properties.getAppSecret(),
				this.properties.getApiVersion());
	}

	@Bean
	@ConditionalOnMissingBean(Facebook.class)
	@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
	public Facebook facebook(ConnectionRepository repository) {
		Connection<Facebook> connection = repository.findPrimaryConnection(Facebook.class);
		return connection != null ? connection.getApi() : null;
	}

	@Bean
	public ConnectController connectController(ConnectionFactoryLocator factoryLocator, ConnectionRepository repository) {
		ConnectController controller = new ConnectController(factoryLocator, repository);
		controller.setApplicationUrl("https://localhost:8443");
		return controller;
	}
}
