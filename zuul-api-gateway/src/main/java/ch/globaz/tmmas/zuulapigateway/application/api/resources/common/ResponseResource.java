package ch.globaz.tmmas.zuulapigateway.application.api.resources.common;

import lombok.Getter;

@Getter
public class ResponseResource {

    private ResourceObject data;

    public ResponseResource(){};

    public ResponseResource(ResourceObject data){
        this.data = data;
    }


}
