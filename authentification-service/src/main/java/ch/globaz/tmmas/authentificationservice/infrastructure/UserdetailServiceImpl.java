package ch.globaz.tmmas.authentificationservice.infrastructure;

import ch.globaz.tmmas.authentificationservice.domain.model.UtilisateursLdap;
import ch.globaz.tmmas.authentificationservice.domain.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserdetailServiceImpl implements UserDetailsService {

	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

		UtilisateursLdap user = utilisateurRepository.getByUUID(s);

		if(user == null){
			throw new RuntimeException("Problem with user search: " + user);
		}


		return new User(user.getUid(),user.getPassword(),new ArrayList<>());


	}
}
