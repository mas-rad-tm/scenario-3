package ch.globaz.tmmas.authentificationservice.infrastructure.authentifcation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestTemplateErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {

        LOGGER.info("clientHttpResponse status code: {}",clientHttpResponse.getStatusCode());

        return !clientHttpResponse.getStatusCode().is2xxSuccessful();

    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {

        LOGGER.error("{} - {}",clientHttpResponse.getStatusCode(),clientHttpResponse.getStatusText());


    }
}
