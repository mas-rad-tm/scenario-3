package ch.globaz.tmmas.zuulapigateway.application.api.resources.common;

import lombok.Getter;

import java.util.List;

@Getter
public class ResponseCollectionResource {

    private List<ResourceObject> data;

    public ResponseCollectionResource(){};

    public ResponseCollectionResource(List<ResourceObject> data){
        this.data = data;
    }
    

}
