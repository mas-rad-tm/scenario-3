package ch.globaz.tmmas.personnesservice.domain.command;

import ch.globaz.tmmas.personnesservice.domain.model.Sexe;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Commande de création d'une personne morale.
 * Par défaut une personne morale est une entité autonome.
 */
@EqualsAndHashCode
@Getter
@ToString
public class CreerPersonneMoraleCommand implements DomainCommand {

	@NotNull
	private String nss;
	@NotNull
	private String nom;
	@NotNull
	private String prenom;
	@NotNull
	private ZonedDateTime dateNaissance;
	@NotNull
	private Sexe sexe;


	CreerPersonneMoraleCommand() {}


}
