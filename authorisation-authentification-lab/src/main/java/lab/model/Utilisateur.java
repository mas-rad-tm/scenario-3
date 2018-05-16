package lab.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
//@ToString
@Entity
@Table(name = "utilisateurs")
public class Utilisateur implements UserDetails{

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String prenom;

    private String nom;

    private String email;

    private String nomUtilisateur;

    @Column(length = 60)
    private String motDePasse;

    private boolean enabled = Boolean.TRUE;

    //private boolean isUsing2FA;

   // private String secret;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "utilisateurs_roles", joinColumns = @JoinColumn(name = "utilisateur_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

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

    public Utilisateur(Long id, String nomUtilisateur, Collection<Role> roles) {
        this();
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role: this.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPermissions().stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName()))
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
