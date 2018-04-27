package ch.globaz.tmmas.personnesservice.domain.common;

public enum GlobalParams {

    DATE_FORMATTER_PATTER("dd.MM.yyyy");

    public String value;

    GlobalParams(String value){
        this.value  = value;
    }
}
