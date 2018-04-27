package ch.globaz.tmmas.personnesservice.domain.model;

import ch.globaz.tmmas.personnesservice.domain.common.ValueObject;
import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * NSS identifiant unique pour une personne morale
 * Le format du nss doit être composé ainsi:
 * - 756.****.****.**
 */
@Getter
@EqualsAndHashCode
@ToString
public class NSS implements ValueObject<NSS>{

	private String nss;

	public NSS(String nss){
		Preconditions.checkNotNull(nss,"Le nss est obligatoire");
		Preconditions.checkArgument(nss.length() == 16,"Le format du nss n'est pas correct");

		this.nss = nss;
	}

	@Override
	public boolean sameValueAs(NSS other) {
		return this.nss.equals(other.nss);
	}

	NSS(){}
}
