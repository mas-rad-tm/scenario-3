package ch.globaz.tmmas.zuulapigateway.infrastructure.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")

public class Utilisateur implements UserDetails{


    private Long id;

    private String prenom;

    private String nom;

    private String email;

    private String nomUtilisateur;


    private String motDePasse;

    private boolean enabled = Boolean.TRUE;

    //private boolean isUsing2FA;

   // private String secret;


    private Set<Role> roles;

    public Utilisateur() {
        super();
        //this.secret = "toto";//Base32.random();
    }

    public Utilisateur(String name, String username, String email, String password) {
        this();
        this.nomUtilisateur = username;
        this.prenom = name;
        this.motDePasse = password;
        this.email = email;
    }

    public Utilisateur(Long id, String nomUtilisateur, Set<Role> roles) {
        this();
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role: this.getRoles()) {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPermissions().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getTypePermission().nom()))
                    .forEach(authorities::add);
        }

        return authorities;
    }


    public List<GrantedAuthority> getAuthoritiesAsList() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role: this.getRoles()) {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPermissions().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getTypePermission().nom()))
                    .forEach(authorities::add);
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return nomUtilisateur;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
