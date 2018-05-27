package ch.globaz.tmmas.personnesservice.infrastructure.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = "users")
public class Role {


    private Long id;


    private Collection<Utilisateur> users;

    private Collection<Permission> permissions;

    private String name;

    public Role() {
        super();
    }

    public Role(final String name) {
        super();
        this.name = name;
    }



}
