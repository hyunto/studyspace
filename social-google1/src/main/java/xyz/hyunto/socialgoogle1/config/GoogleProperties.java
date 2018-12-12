package xyz.hyunto.socialgoogle1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spring.social.google")
public class GoogleProperties {

	private String appId;

	private String appSecret;

}
