package ch.globaz.tmmas.personnesservice.application.api.web.resources.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
public class ResponseResource {

    private ResourceObject data;

    public ResponseResource(){};

    public ResponseResource(ResourceObject data){
        this.data = data;
    }


}
