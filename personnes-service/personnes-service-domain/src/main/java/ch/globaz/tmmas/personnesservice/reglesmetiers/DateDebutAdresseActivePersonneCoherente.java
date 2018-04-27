package ch.globaz.tmmas.personnesservice.reglesmetiers;

import ch.globaz.tmmas.personnesservice.domain.common.specification.AbstractSpecification;

import java.time.Period;
import java.time.ZonedDateTime;
import java.util.List;


public class DateDebutAdresseActivePersonneCoherente extends AbstractSpecification<ZonedDateTime> {

    private ZonedDateTime dateDebutAdresseActive;


    public DateDebutAdresseActivePersonneCoherente(ZonedDateTime dateDebutAdresseActive){
        super();
        this.dateDebutAdresseActive = dateDebutAdresseActive;

    }

    @Override
    public boolean isSatisfiedBy(ZonedDateTime dateDebutNouvelleAdresse) {

        return dateDebutNouvelleAdresse.isBefore(dateDebutAdresseActive.plus(Period.ofDays(1)));

    }

    @Override
    public List<String> getDescriptionReglesMetier() {
        return null;
    }
}
