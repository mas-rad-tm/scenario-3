package ch.globaz.tmmas.personnesservice.reglesmetiers;

import ch.globaz.tmmas.personnesservice.domain.common.specification.AbstractSpecification;
import ch.globaz.tmmas.personnesservice.domain.repository.PersonneRepository;

import java.util.List;


public class NssNonExistant extends AbstractSpecification<String> {

    private PersonneRepository personneRepository;

    public NssNonExistant(PersonneRepository personneRepository){
        super();
        this.personneRepository = personneRepository;
    }

    @Override
    public boolean isSatisfiedBy(String nss) {
        return !personneRepository.getPersonneByNss(nss).isPresent();
    }

    @Override
    public List<String> getDescriptionReglesMetier() {
        return null;
    }
}
