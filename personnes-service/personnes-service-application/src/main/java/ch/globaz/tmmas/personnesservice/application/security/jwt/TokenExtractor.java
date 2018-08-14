package ch.globaz.tmmas.personnesservice.application.security.jwt;

/**
 * L'implémentation de cette interface devrait toujours retournée
 * une représentation en Base64 du token jwt
 */
public interface TokenExtractor {

    /**
     * Extraction du token jwt du header http
     * @param header le header http duquelextraire le token
     * @return le token jwt
     */
    public String extract(String header);

}
