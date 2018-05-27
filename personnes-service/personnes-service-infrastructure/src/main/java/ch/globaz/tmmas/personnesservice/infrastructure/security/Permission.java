package ch.globaz.tmmas.personnesservice.infrastructure.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode

public class Permission implements GrantedAuthority {


    private Long id;

    private String name;

    private Collection<Role> roles;

    public Permission() {
        super();
    }

    public Permission(final String name) {
        super();
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
