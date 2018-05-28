package ch.globaz.tmmas.personnesservice.application.configuration;

import ch.globaz.tmmas.personnesservice.infrastructure.repository.PermissionsHibernateRepository;
import ch.globaz.tmmas.personnesservice.infrastructure.repository.RoleHibernateRepository;
import ch.globaz.tmmas.personnesservice.infrastructure.repository.UtilisateurHibernateRepository;
import ch.globaz.tmmas.personnesservice.infrastructure.security.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    List<Permission> permissions = new ArrayList<>();



    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        logger.info("Startig inserting sample datas....");

        if (alreadySetup){
            return;
        }

        logger.info("****** Création des permissions");



        getInitialPermissions().stream().forEach(permission -> {
            createPermissionsIfNotFound(permission);
            permissions.add(permission);
            logger.info("Permission créé : {}", permission);
        });


        logger.info("****** Création des roles");

        Role roleResponsable = new Role(TypeRole.RESPONSABLE);

        Set<Permission> roleResponsablePermissions = new HashSet<Permission>();
        roleResponsablePermissions.add(getPermissionFor(TypePermission.CREATE_ADRESSE));
        roleResponsablePermissions.add(getPermissionFor(TypePermission.READ_ADRESSE));
        roleResponsablePermissions.add(getPermissionFor(TypePermission.CREATE_PERSONNE));
        roleResponsablePermissions.add(getPermissionFor(TypePermission.READ_PERSONNE));

        createRoleIfNotFound(roleResponsable,roleResponsablePermissions);
        logger.info("Role créé : {}", roleResponsable);


        Role roleGestionnaire = new Role(TypeRole.GESTIONNAIRE);

        Set<Permission> roleGestionnairePermissions = new HashSet<Permission>();
        roleGestionnairePermissions.add(getPermissionFor(TypePermission.CREATE_ADRESSE));
        roleGestionnairePermissions.add(getPermissionFor(TypePermission.READ_ADRESSE));
        roleGestionnairePermissions.add(getPermissionFor(TypePermission.READ_PERSONNE));

        createRoleIfNotFound(roleGestionnaire,roleResponsablePermissions);
        logger.info("Role créé : {}", roleGestionnaire);


        Role roleStagiaire = new Role(TypeRole.STAGIAIRE);

        Set<Permission> roleStagiairePermissions = new HashSet<Permission>();
        roleStagiairePermissions.add(getPermissionFor(TypePermission.READ_PERSONNE));

        createRoleIfNotFound(roleStagiaire,roleStagiairePermissions);
        logger.info("Role créé : {}", roleStagiaire);



        logger.info("Création des utilisateurs");
        Utilisateur sce = new Utilisateur();
        sce.setNom("Chèvre");
        sce.setPrenom("Sébastien");
        sce.setNomUtilisateur("sce");
        sce.setMotDePasse(passwordEncoder.encode("scePass"));
        sce.setEmail("sce@sce.com");

        HashSet roles = new HashSet();
        roles.add(roleResponsable);
        sce.setRoles(roles);
        sce.setEnabled(true);
        utilisateurRepository.save(sce);


        Utilisateur mmo = new Utilisateur();
        mmo.setNom("Mickey");
        mmo.setPrenom("Mouse");
        mmo.setNomUtilisateur("mmo");
        mmo.setMotDePasse(passwordEncoder.encode("mmoPass"));
        mmo.setEmail("mmo@sce.com");

        roles = new HashSet();
        roles.add(roleGestionnaire);
        mmo.setRoles(roles);
        mmo.setEnabled(true);
        utilisateurRepository.save(mmo);

        Utilisateur pic = new Utilisateur();
        pic.setNom("Picsou");
        pic.setPrenom("Duck");
        pic.setNomUtilisateur("pic");
        pic.setMotDePasse(passwordEncoder.encode("mmoPass"));
        pic.setEmail("mmo@sce.com");

        roles = new HashSet();
        roles.add(roleStagiaire);
        pic.setRoles(roles);
        pic.setEnabled(true);
        utilisateurRepository.save(pic);

        logger.info("Data sample successfully inserted");

        alreadySetup = true;

    }

    List<Permission> getInitialPermissions(){
        return Arrays.asList(TypePermission.values()).stream().map(typePermission -> {
            return new Permission(typePermission);
        }).collect(Collectors.toList());
    }

    Permission getPermissionFor(TypePermission type){

        return permissions.stream().filter(permission -> {
            return permission.getTypePermission().equals(type);
        }).findFirst().get();

    }


    @Transactional
    Permission createPermissionsIfNotFound(Permission permission) {

        permissionsRepository.findByTypePermission(permission.getTypePermission()).orElseGet(() -> {
             permissionsRepository.save(permission);
            return permission;
        });


        return permission;
    }

    @Transactional
    Role createRoleIfNotFound(Role role, Set<Permission> permissions) {

        roleRepository.findByRoleType(role.getTypeRole()).orElseGet(()->{
            role.setPermissions(permissions);
            roleRepository.save(role);
            return role;
        });

        return role;
    }
}
