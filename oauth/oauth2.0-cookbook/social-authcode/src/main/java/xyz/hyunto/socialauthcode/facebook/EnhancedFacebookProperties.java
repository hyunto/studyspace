package xyz.hyunto.socialauthcode.facebook;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "facebook")
public class EnhancedFacebookProperties {

	private String appId;

	private String appSecret;

	private String apiVersion;

}
