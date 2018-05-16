package lab.application;


import lab.application.security.JwtTokenProvider;
import lab.model.Utilisateur;
import lab.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UtilisateurDetailService implements UserDetailsService {


    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Transactional
    public UserDetails loadUserByUId(Long id) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository.findById(id).get();
        return utilisateur;
    }

    public UserDetails getUserFromToken(String token) {
        return new JwtTokenProvider().getUserdFromJWT(token);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String nomUtilisateur) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurRepository.findByNomUtilisateur(nomUtilisateur);
        return utilisateur;
    }


}
