package ch.globaz.tmmas.authentificationservice.infrastructure.repository;

import ch.globaz.tmmas.authentificationservice.domain.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Repository;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.Iterator;
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
    public void authenticate(final String username, final String password) {

        contextSource.getContext("uid=" + username + ",ou=utilisateurs," + env.getRequiredProperty("spring.ldap.embedded.partitionSuffix"), password);
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
