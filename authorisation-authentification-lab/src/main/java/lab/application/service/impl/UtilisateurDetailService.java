package lab.application.service.impl;


import lab.application.security.JwtTokenProvider;
import lab.application.service.UtilisateurService;
import lab.model.Utilisateur;
import lab.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UtilisateurDetailService implements UtilisateurService {


    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Transactional
    public Optional<Utilisateur> loadUserByUId(Long id) throws UsernameNotFoundException {

        return utilisateurRepository.findById(id);
    }

    public UserDetails getUserFromToken(String token) {
        return new JwtTokenProvider().getUserdFromJWT(token);
    }

    @Transactional
    @Override
    public Optional<Utilisateur> loadUserByUsername(String nomUtilisateur) throws UsernameNotFoundException {

        return utilisateurRepository.findByNomUtilisateur(nomUtilisateur);

    }


}
