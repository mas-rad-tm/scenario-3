package ch.globaz.tmmas.personnesservice.application.configuration;

import ch.globaz.tmmas.personnesservice.infrastructure.authentifcation.RestTemplateErrorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    private final RestTemplateErrorHandler restTemplateErrorHandler;

    @Autowired
    public RestTemplateConfiguration(RestTemplateErrorHandler restTemplateErrorHandler){
        this.restTemplateErrorHandler = restTemplateErrorHandler;
    }

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(restTemplateErrorHandler);
        return restTemplate;
    }
}
