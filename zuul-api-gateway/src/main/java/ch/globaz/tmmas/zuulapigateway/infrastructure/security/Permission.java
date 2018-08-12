package ch.globaz.tmmas.zuulapigateway.infrastructure.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

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
