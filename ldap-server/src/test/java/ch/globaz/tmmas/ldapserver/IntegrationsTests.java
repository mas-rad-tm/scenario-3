package ch.globaz.tmmas.ldapserver;


import ch.globaz.tmmas.ldapserver.domain.UtilisateurService;

import ch.globaz.tmmas.ldapserver.domain.model.UtilisateursLdap;
import ch.globaz.tmmas.ldapserver.infrastructure.UtilisateurLdapService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@ContextConfiguration(classes = {TestConfiguration.class,UtilisateurLdapService.class})
@SpringBootTest
@ComponentScan(basePackages = {"ch.globaz.tmmas.ldapserver"})
@EnableLdapRepositories(basePackages = "ch.globaz.tmmas.ldapserver.**")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@RunWith(SpringRunner.class)
public class IntegrationsTests {

	@Autowired
	private UtilisateurService utilisateurService;

	@Test
	public void testGetAllPersonnes() {

		List<UtilisateursLdap> persons = utilisateurService.getAllPersonnes();
		assertNotNull(persons);
		assertEquals(persons.size(), 3);

		List<String> uids = persons.stream().map(personne -> {
			return personne.getUid();
		}).collect(Collectors.toList());

		assertThat(uids).contains("mmo", "pic", "sce");

		List<String> noms = persons.stream().map(personne -> {
			return personne.getNom();
		}).collect(Collectors.toList());

		assertThat(noms).contains("Mouse", "Duck", "Chèvre");

		List<String> prenoms = persons.stream().map(personne -> {
			return personne.getPrenom();
		}).collect(Collectors.toList());

		assertThat(prenoms).contains("Mickey", "Picsou", "Sébastien");

	}

	@Test
	public void testGetRolesByPersonne() {

		List<String> sceRoles = utilisateurService.getRolesFor("sce");
		assertThat(sceRoles).contains("gestionnaire","responsable");

		List<String> mmoRoles = utilisateurService.getRolesFor("mmo");
		assertThat(mmoRoles).contains("gestionnaire");

		List<String> picRoles = utilisateurService.getRolesFor("pic");
		assertThat(picRoles).contains("stagiaire");
	}



	@Test
	public void testIfUserModelIsCorrectlyFilledWithRoles(){
		UtilisateursLdap sce = utilisateurService.getByUUID("sce");
		assertThat(sce.getRoles().size()).isEqualTo(2);
	}

	@Test
	public void testAllUserAuthentication() {

		UtilisateursLdap sce = utilisateurService.authenticate("sce","secret");
		assertThat(sce).isNotNull();
		assertThat(sce.getRoles().size()).isEqualTo(2);
		assertThat(sce.getUid()).isEqualTo("sce");
		assertThat(sce.getRoles()).contains("responsable","gestionnaire");

		UtilisateursLdap mmo = utilisateurService.authenticate("mmo","secret");
		assertThat(mmo).isNotNull();
		assertThat(mmo.getRoles().size()).isEqualTo(1);
		assertThat(mmo.getUid()).isEqualTo("mmo");
		assertThat(sce.getRoles()).contains("gestionnaire");

		UtilisateursLdap pic = utilisateurService.authenticate("pic","secret");
		assertThat(pic).isNotNull();
		assertThat(pic.getRoles().size()).isEqualTo(1);
		assertThat(pic.getUid()).isEqualTo("pic");
		assertThat(pic.getRoles()).contains("stagiaire");

	}
}
