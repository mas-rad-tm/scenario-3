package ch.globaz.tmmas.authentificationservice.application.jwt;

public enum ClaimKeyEnum {
    CLAIM_KEY_USERID("id"),
    CLAIM_KEY_USERNAME("nomUtilisateur"),
    CLAIM_KEY_CREATED("created"),
    CLAIM_KEY_NAME("name"),
    CLAIM_KEY_ADMIN("admin"),
    CLAIM_KEY_AUTH_TYPE("auttype"),
    CLAIM_KEY_EMAIL("email");

    private final String value;

    /**
     * @param value
     */
    private ClaimKeyEnum(String value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

}
