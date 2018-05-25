package ch.globaz.tmmas.ldapserver.infrastructure;

import ch.globaz.tmmas.ldapserver.domain.UtilisateurService;
import ch.globaz.tmmas.ldapserver.domain.model.UtilisateursLdap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.AuthenticationException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class UtilisateurLdapService implements UtilisateurService {
    @Autowired
    private Environment env;

    @Autowired
    private ContextSource contextSource;

    @Autowired
    private LdapTemplate ldapTemplate;



    /**
     * Retourne la liste des utilisateurs de l'AD
     * @return la liste des utilisateurs AD
     */
    @Override
    public List<UtilisateursLdap> getAllPersonnes() {
        return ldapTemplate.search(
                query().where("objectclass").is("person"),
                new AttributesMapper<UtilisateursLdap>() {
                    public UtilisateursLdap mapFromAttributes(Attributes attrs)
                            throws NamingException {

                        String uid = (String) attrs.get("uid").get();
                        String prenom = ((String) attrs.get("cn").get()).split(" ")[0];
                        String nom = ((String) attrs.get("cn").get()).split(" ")[1];

                        UtilisateursLdap user = new UtilisateursLdap(uid,nom, prenom,null);

                        return user;
                    }
                });
    }

    /**
     * Recherche d'un utilisateur par son nom
     * @param username le nom d'utilisateur (uid) à rechercher
     * @return une liste de nom retrouvé
     */
    @Override
    public List<String> searchByUsername(final String username) {
        return ldapTemplate.search(
                "ou=utilisateurs, dc=globaz.tmmas, dc=ch",
                "uid=" + username,
                (AttributesMapper<String>) attrs -> (String) attrs
                        .get("cn")
                        .get());
    }

    /**
     * Retourne un utilisateur par son uid
     * @param uid l'uid de l'utilisateur
     * @return une instance de {@link UtilisateursLdap}
     */
    @Override
    public UtilisateursLdap getByUUID(final String uid) {


        List<UtilisateursLdap> users = ldapTemplate.search(
                "ou=utilisateurs, dc=globaz.tmmas, dc=ch",
                "uid=" + uid,
                (AttributesMapper<UtilisateursLdap>) attrs -> {

                    String userId = (String) attrs.get("uid").get();
                    String prenom = ((String) attrs.get("cn").get()).split(" ")[0];
                    String nom = ((String) attrs.get("cn").get()).split(" ")[1];
                    List<String> roles = getRolesFor(uid);

                    return new UtilisateursLdap(userId,nom,prenom,roles);

                });

        return users.get(0);
    }

    @Override
    public UtilisateursLdap authenticate(final String username, final String password) {

        UtilisateursLdap utilisateur;

        try{
            contextSource.getContext("uid=" + username + ",ou=utilisateurs," + env.getRequiredProperty
                    ("spring.ldap" +
                            ".embedded.partitionSuffix"), password);

            utilisateur = getByUUID(username);
        }catch(AuthenticationException ex){
            throw ex;
        }

        return utilisateur;
    }

    @Override
    public List<String> getRoles() {
        return ldapTemplate.search(
                query().base("ou=roles,dc=globaz.tmmas,dc=ch"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return (String) attrs.get("cn").get();
                    }
                });
    }

    /**
     * Retrieves all the persons in the ldap server
     * @return list of person names
     */
    @Override
    public List<String> getRolesFor(String uid) {

        return ldapTemplate.search(
                query().base("ou=roles,dc=globaz.tmmas,dc=ch").where("uniqueMember").is("uid=" + uid +
                        ",ou=utilisateurs,dc=globaz.tmmas,dc=ch"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return (String) attrs.get("cn").get();
                    }
                });
    }
}
