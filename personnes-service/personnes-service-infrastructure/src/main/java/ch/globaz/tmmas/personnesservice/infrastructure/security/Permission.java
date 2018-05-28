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

    private TypePermission typePermission;

    private Collection<Role> roles;

    public Permission() {
        super();
    }

    public Permission(final TypePermission typePermission) {
        super();
        this.typePermission = typePermission;
    }

    @Override
    public String getAuthority() {
        return this.typePermission.nom();
    }
}
