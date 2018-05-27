package ch.globaz.tmmas.personnesservice.application.configuration;

import ch.globaz.tmmas.personnesservice.infrastructure.repository.PermissionsHibernateRepository;
import ch.globaz.tmmas.personnesservice.infrastructure.repository.RoleHibernateRepository;
import ch.globaz.tmmas.personnesservice.infrastructure.repository.UtilisateurHibernateRepository;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Permission;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Role;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Roles;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Utilisateur;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Permissions;
import java.util.*;

/**
 * Classe insérant des données initiales de bases
 * Est executé une fois le contexte de l'application démarré
 */
@Component
public class InitialDataConfiguration implements
        ApplicationListener<ContextRefreshedEvent> {


    boolean alreadySetup = false;

    @Autowired
    private UtilisateurHibernateRepository utilisateurRepository;

    @Autowired
    private RoleHibernateRepository roleRepository;

    @Autowired
    private PermissionsHibernateRepository permissionsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(InitialDataConfiguration.class);




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
        Set<Permission> adminPermissions = new HashSet<>();
        adminPermissions.add(readPermission);
        adminPermissions.add(writePermission);

        Set<Permission> readPermissions = new HashSet<>();
        readPermissions.add(readPermission);


        createRoleIfNotFound(Roles.ADMIN.nom, adminPermissions);
        createRoleIfNotFound(Roles.USER.nom, readPermissions);

        Role adminRole = roleRepository.findByName(Roles.ADMIN.nom).get();

        logger.info("Création des utilisateurs");
        Utilisateur user = new Utilisateur();
        user.setNom("Chèvre");
        user.setPrenom("Sébastien");
        user.setNomUtilisateur("sce");
        user.setMotDePasse(passwordEncoder.encode("scePass"));
        user.setEmail("sce@sce.com");

        HashSet roles = new HashSet();
        roles.add(adminRole);
        user.setRoles(roles);
        user.setEnabled(true);
        utilisateurRepository.save(user);

        Role userRole = roleRepository.findByName(Roles.USER.nom).get();

        Utilisateur user2 = new Utilisateur();
        user2.setNom("Mickey");
        user2.setPrenom("Mouse");
        user2.setNomUtilisateur("mmo");
        user2.setMotDePasse(passwordEncoder.encode("mmoPass"));
        user2.setEmail("mmo@sce.com");

        roles = new HashSet();
        roles.add(userRole);
        user2.setRoles(roles);
        user2.setEnabled(true);
        utilisateurRepository.save(user2);

        logger.info("Data sample successfully inserted");

        alreadySetup = true;

    }

    @Transactional
    Permission createPermissionsIfNotFound(String name) {

        Permission permission = permissionsRepository.findByName(name).orElseGet(() -> {
            Permission p = new Permission(name);
            permissionsRepository.save(p);
            return p;
        });


        return permission;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Set<Permission> permissions) {

        Role role = roleRepository.findByName(name).orElseGet(()->{
            Role r = new Role(name);
            r.setPermissions(permissions);
            roleRepository.save(r);
            return r;
        });

        return role;
    }
}
