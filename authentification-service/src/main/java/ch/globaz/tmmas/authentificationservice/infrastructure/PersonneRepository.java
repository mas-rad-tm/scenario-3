package ch.globaz.tmmas.authentificationservice.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Repository
public class PersonneRepository {

	@Autowired
	private LdapTemplate ldapTemplate;

	/**
	 * Retrieves all the persons in the ldap server
	 * @return list of person names
	 */
	public List<String> getAllPersonNames() {
		return ldapTemplate.search(
				query().where("objectclass").is("person"),
				(AttributesMapper<String>) attrs -> {
					return (String) attrs.get("cn").get();
				});
	}


}
