package lab.application.security;

import lab.model.Role;
import lab.model.Utilisateur;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
public class JwtUserDetailClaim {

    private String nomUtilisateur;

    private Collection<Role> roles;

    private Long id;

    private JwtUserDetailClaim (String nomUtilisateur, Long id, Collection<Role> roles) {
        this.nomUtilisateur = nomUtilisateur;
        this.id = id;
        this.roles = roles;
    }

    public static JwtUserDetailClaim generateFromUtilisateur (Utilisateur utilisateur) {
        return new JwtUserDetailClaim(utilisateur.getNomUtilisateur(),
                utilisateur.getId(),
                utilisateur.getRoles());
    }

}
