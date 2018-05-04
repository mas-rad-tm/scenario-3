package ch.globaz.tmmas.authentificationservice.infrastructure.repository;

import ch.globaz.tmmas.authentificationservice.domain.model.UtilisateursLdap;
import ch.globaz.tmmas.authentificationservice.domain.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Repository
public class UtilisateurLdapRepository implements UtilisateurRepository{

    @Autowired
    private Environment env;

    @Autowired
    private ContextSource contextSource;

    @Autowired
    private LdapTemplate ldapTemplate;

    /**
     * Retrieves all the persons in the ldap server
     * @return list of person names
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



                    return new UtilisateursLdap((String)attrs.get("uid").get(),(String)attrs.get("userpassword").get());

                });

        return users.get(0);
/**
        return ldapTemplate.search(
                "ou=utilisateurs, dc=globaz.tmmas, dc=ch",
                "uid=" + username,
                (AttributesMapper<String>) attrs -> (String) attrs
                        .get("cn")
                        .get());
 */
    }

    @Override
    public UtilisateursLdap authenticate(final String username, final String password) {

        /**
        Filter filter = new EqualsFilter("uid", username);

        boolean authed = ldapTemplate.authenticate("dc=globaz.tmmas, dc=ch",
                filter.encode(),
                password);
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
                query().base("ou=groupes").where("objectClass").is("person"),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return (String) attrs.get("cn").get();
                    }
                });
    }


}
