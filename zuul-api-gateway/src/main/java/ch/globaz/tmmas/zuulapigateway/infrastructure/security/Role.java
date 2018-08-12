package ch.globaz.tmmas.zuulapigateway.infrastructure.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"users","permissions"})
@ToString(exclude = {"users","permissions"})
public class Role {


    private Long id;


    private Collection<Utilisateur> users;

    private Collection<Permission> permissions;

    private TypeRole typeRole;

    public Role() {
        super();
    }

    public Role(final TypeRole typeRole) {
        super();
        this.typeRole = typeRole;
    }



}
