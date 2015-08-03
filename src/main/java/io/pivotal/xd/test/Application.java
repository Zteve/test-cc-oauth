package io.pivotal.xd.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

@SpringBootApplication(exclude = OAuth2AutoConfiguration.class)
public class Application {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        applicationContext.getBean(Sample.class).getSomething();
    }

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
                                                 OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oauth2ClientContext);
    }

    @Bean
    @ConfigurationProperties("security.oauth2.client")
    OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails() {
        return new ResourceOwnerPasswordResourceDetails();
    }

    @Bean
    public OAuth2ClientContext oauth2ClientContext() {
        return new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());
    }
}
