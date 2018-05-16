package lab.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode
//@ToString
@Entity
@Table(name = "permission")
public class Permission implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "permissions",fetch = FetchType.EAGER)
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
