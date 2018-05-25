package ch.globaz.tmmas.ldapserver.infrastructure;

import ch.globaz.tmmas.ldapserver.domain.UtilisateurService;
import ch.globaz.tmmas.ldapserver.domain.model.UtilisateursLdap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

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
     * @return la liste des noms des personnes (cn)
     */
    @Override
    public List<String> getAllPersonNames() {
        return ldapTemplate.search(
                query().where("objectclass").is("person"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return (String) attrs.get("cn").get();
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

    @Override
    public UtilisateursLdap getByUUID(final String uid) {


        List<UtilisateursLdap> users = ldapTemplate.search(
                "ou=utilisateurs, dc=globaz.tmmas, dc=ch",
                "uid=" + uid,
                (AttributesMapper<UtilisateursLdap>) attrs -> {
                    System.out.println(attrs);
                    System.out.println(attrs.get("userpassword"));
                    System.out.println(attrs.get("entryDN").get(0));
                    System.out.println(attrs.get("uid"));



                    return new UtilisateursLdap((String)attrs.get("uid").get(),(String)attrs.get("userpassword").get().toString());

                });

        return users.get(0);
/**
 return ldapTemplate.search(
 "ou=utilisateurs, dc=globaz.tmmas, dc=ch",
 "uid=" + nomUtilisateur,
 (AttributesMapper<String>) attrs -> (String) attrs
 .get("cn")
 .get());
 */
    }

    @Override
    public UtilisateursLdap authenticate(final String username, final String password) {

        /**
         Filter filter = new EqualsFilter("uid", nomUtilisateur);

         boolean authed = ldapTemplate.authenticate("dc=globaz.tmmas, dc=ch",
         filter.encode(),
         motDePasse);
         */



        //System.out.println("Auth:" + authed);

        contextSource.getContext("uid=" + username + ",ou=utilisateurs," + env.getRequiredProperty("spring.ldap" +
                ".embedded.partitionSuffix"), password);

        return new UtilisateursLdap(username, password);
    }

    /**
     * Retrieves all the persons in the ldap server
     * @return list of person names
     */
    @Override
    public List<String> getGroupes() {


        return ldapTemplate.search(
                query().base("ou=groupes,dc=globaz.tmmas,dc=ch").where("uniqueMember").is("uid=pic,ou=utilisateurs,dc=globaz.tmmas,dc=ch"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return (String) attrs.get("cn").get();
                    }
                });
    }
}
