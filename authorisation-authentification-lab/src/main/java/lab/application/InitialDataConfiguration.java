package lab.application;

import lab.model.Permission;
import lab.model.Role;
import lab.model.Roles;
import lab.model.Utilisateur;
import lab.repository.Permissions;
import lab.repository.RoleRepository;
import lab.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Classe insérant des données initiales de bases
 * Est executé une fois le contexte de l'application démarré
 */
@Component
public class InitialDataConfiguration implements
        ApplicationListener<ContextRefreshedEvent> {


    boolean alreadySetup = false;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Permissions permissions;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(InitialDataConfiguration.class);


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        logger.info("Startig inserting sample datas....");

        if (alreadySetup){
            return;
        }

        logger.info("Création des permissions");
        Permission readPermission = createPermissionsIfNotFound("READ_PERMISSION");
        Permission writePermission = createPermissionsIfNotFound("WRITE_PERMISSION");

        logger.info("Création des roles");
        List<Permission> adminPermissions = Arrays.asList(readPermission, writePermission);
        createRoleIfNotFound(Roles.ADMIN.nom, adminPermissions);
        createRoleIfNotFound(Roles.USER.nom, Arrays.asList(readPermission));

        Role adminRole = roleRepository.findByName(Roles.ADMIN.nom);

        logger.info("Création des utilisateurs");
        Utilisateur user = new Utilisateur();
        user.setNom("Chèvre");
        user.setPrenom("Sébastien");
        user.setNomUtilisateur("sce");
        user.setMotDePasse(passwordEncoder.encode("scePass"));
        user.setEmail("sce@sce.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        utilisateurRepository.save(user);

        Role userRole = roleRepository.findByName(Roles.USER.nom);

        Utilisateur user2 = new Utilisateur();
        user2.setNom("Mickey");
        user2.setPrenom("Mouse");
        user2.setNomUtilisateur("mmo");
        user2.setMotDePasse(passwordEncoder.encode("mmoPass"));
        user2.setEmail("mmo@sce.com");
        user2.setRoles(Arrays.asList(userRole));
        user2.setEnabled(true);
        utilisateurRepository.save(user2);

        logger.info("Data sample successfully inserted");

        alreadySetup = true;

    }

    @Transactional
    Permission createPermissionsIfNotFound(String name) {

        Permission permission = permissions.findByName(name);
        if (permission == null) {
            permission = new Permission(name);
            permissions.save(permission);
        }
        return permission;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Permission> permissions) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPermissions(permissions);
            roleRepository.save(role);
        }
        return role;
    }
}
