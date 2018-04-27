package ch.globaz.tmmas.personnesservice.domain.factory;

import ch.globaz.tmmas.personnesservice.domain.command.CreerAdresseCommand;
import ch.globaz.tmmas.personnesservice.domain.exception.AdresseIncoherenceException;
import ch.globaz.tmmas.personnesservice.domain.model.Adresse;
import ch.globaz.tmmas.personnesservice.domain.model.Localite;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;
import ch.globaz.tmmas.personnesservice.domain.repository.LocaliteRepository;
import ch.globaz.tmmas.personnesservice.domain.repository.PersonneRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public class AdresseFactory {


    public static Adresse create(CreerAdresseCommand command, PersonneMorale personneMorale, LocaliteRepository localiteRepository) throws AdresseIncoherenceException {




        //if(personneMorale.isPresent()){
            return localiteRepository.findById(command.getLocaliteId()).map(localite -> {


                return new Adresse(localite,personneMorale,command.getRue(),command.getNumero(),command.getComplement
                        (), command.getDateDebutValidite());

            }).orElseThrow(() -> {
                return new AdresseIncoherenceException("La localite avec l'id "  + command.getLocaliteId() + " n'existe pas");
            });
       // }else{
        //    throw new AdresseIncoherenceException("La personne avec l'id "  + personneId + " n'existe pas");
        //}


    }


}
