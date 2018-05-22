package lab.application.api.web.rest;

import lab.application.api.web.ApiResponse;
import lab.application.api.web.request.SignUpRequest;
import lab.model.Role;
import lab.model.Utilisateur;
import lab.repository.RoleRepository;
import lab.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class SignupController {

    @Autowired
    UtilisateurRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    private static final Logger logger = LoggerFactory.getLogger(SignupController.class);

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        /**
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
**/
        // Creating user's account
        Utilisateur utilisateur = new Utilisateur(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());

        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER");

        //        .orElseThrow(() -> new AppException("User Role not set."));

        utilisateur.setRoles(Collections.singleton(userRole));

        Utilisateur result = userRepository.save(utilisateur);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{nomUtilisateur}")
                .buildAndExpand(result.getNomUtilisateur()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}
