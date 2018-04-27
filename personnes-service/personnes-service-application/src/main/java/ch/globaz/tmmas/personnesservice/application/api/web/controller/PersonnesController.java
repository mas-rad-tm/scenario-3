package ch.globaz.tmmas.personnesservice.application.api.web.controller;

import ch.globaz.tmmas.personnesservice.application.api.web.resources.AdresseResourceAttributes;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.PersonneMoraleResourceAttributes;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ErrorResponseResource;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ResourceObject;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ResponseCollectionResource;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ResponseResource;
import ch.globaz.tmmas.personnesservice.application.event.InternalCommandPublisher;
import ch.globaz.tmmas.personnesservice.application.event.InternalEventPublisher;
import ch.globaz.tmmas.personnesservice.application.service.AdressesService;
import ch.globaz.tmmas.personnesservice.application.service.PersonneService;
import ch.globaz.tmmas.personnesservice.domain.command.CreerAdresseCommand;
import ch.globaz.tmmas.personnesservice.domain.command.CreerPersonneMoraleCommand;
import ch.globaz.tmmas.personnesservice.domain.exception.AdresseIncoherenceException;
import ch.globaz.tmmas.personnesservice.domain.exception.PersonnesIncoherenceException;
import ch.globaz.tmmas.personnesservice.domain.factory.AdresseFactory;
import ch.globaz.tmmas.personnesservice.domain.model.Adresse;
import ch.globaz.tmmas.personnesservice.domain.model.Localite;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;
import ch.globaz.tmmas.personnesservice.domain.repository.AdressesRepository;
import ch.globaz.tmmas.personnesservice.domain.repository.LocaliteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Controlleur REST pour les opérations de base sur les personnes
 */
@RestController
@RequestMapping("/personnes")
public class PersonnesController {

    private static final String PERSONNES = "/personnes";
    private static final String PERSONNE = PERSONNES + "/{id}";
    private static final String ADRESSES = PERSONNES + "/{id}/adresses";

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonnesController.class);
    private static final String LIST_ADRESSES = "adresses";
    private static final String ADD_ADRESSE = "addAdresse";

    @Autowired
    PersonneService personneService;

    @Autowired
    AdressesService adresseService;

    @Autowired
    InternalCommandPublisher commandPublisher;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity listerPersonne()  {

        LOGGER.info("listerPersonneMorale()");

        List<PersonneMorale> personnes = personneService.getAllPersonnes();

        List<ResourceObject> list = personnes.stream().map(personneMorale -> {
            ResourceObject resourceObject =  new PersonneMoraleResourceAttributes(personneMorale)
                    .buildResourceObject();
            try {
                putSelfLink(resourceObject);
            } catch (AdresseIncoherenceException e) {
                throw new RuntimeException(e);
            }
            return resourceObject;
        }).collect(Collectors.toList());


        return new ResponseEntity<>(new ResponseCollectionResource(list), HttpStatus
                .CREATED);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity creerPersonne(@Valid @RequestBody CreerPersonneMoraleCommand command) throws PersonnesIncoherenceException, AdresseIncoherenceException {

        LOGGER.info("creerPersonneMorale(): {}", command);
        commandPublisher.publishCommand(command);


        PersonneMorale personneMorale  = personneService.creerPersonneMorale(command);

        ResourceObject persoResourceObject = new PersonneMoraleResourceAttributes(personneMorale)
                .buildResourceObject();

        putSelfLink(persoResourceObject);




        return new ResponseEntity<>(new ResponseResource(persoResourceObject),putLocationHeader(persoResourceObject),
                HttpStatus.CREATED);
    }

    @RequestMapping(value="/{personneId}/adresses", method = RequestMethod.POST)
    public ResponseEntity creerAdresseForPersonne(@Valid @RequestBody CreerAdresseCommand command, @PathVariable Long
            personneId) throws AdresseIncoherenceException {

        LOGGER.info("creerAdresses() for personnid: {}, command:{}", personneId, command);
        commandPublisher.publishCommand(command);

        //si pas de ressources 404
        Boolean personneExist = personneService.checkifPersonneExist(personneId);


        if(personneExist){


            Adresse nouvelleAdresses = adresseService.createAdresse(command,personneId);

            ResourceObject adresseResource = new AdresseResourceAttributes(nouvelleAdresses)
                    .buildResourceObject();

            return new ResponseEntity<>(new ResponseResource(adresseResource), putLocationHeader(adresseResource), HttpStatus.CREATED);


        }else{
            return new ResponseEntity(new ErrorResponseResource(HttpStatus.NOT_FOUND,"No persone found with id "
                    + personneId),HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{personneId}", method = RequestMethod.GET)
    public ResponseEntity getPersonneById(@PathVariable Long personneId) throws AdresseIncoherenceException {

        LOGGER.info("récupérerPersonnes() for personnid: {}", personneId);

        //si pas de ressources 404
        Boolean personneExist = personneService.checkifPersonneExist(personneId);

        if(personneExist){

            PersonneMorale personneMorale =  personneService.getPersonneById(personneId).get();


            ResourceObject persoResourceObject = new PersonneMoraleResourceAttributes(personneMorale)
                    .buildResourceObject();

            return new ResponseEntity<>(new ResponseResource(persoResourceObject), HttpStatus.OK);

        }else{
            return new ResponseEntity(new ErrorResponseResource(HttpStatus.NOT_FOUND,"No persone found with id "
                    + personneId),HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value="/{personneId}/adresses", method = RequestMethod.GET)
    public ResponseEntity listerAdresseForPersonne(@PathVariable Long
            personneId) throws AdresseIncoherenceException {

        LOGGER.info("listerAdresses() for personnid: {}", personneId);


        //si pas de ressources 404
        Boolean personneExist = personneService.checkifPersonneExist(personneId);


        if(personneExist){

            List<Adresse> adresses = adresseService.listerAdresseForPersonne(personneId);

            List<ResourceObject> adressesResource = adresses.stream().map(adresse -> {
                return new AdresseResourceAttributes(adresse).buildResourceObject();
            }).collect(Collectors.toList());

            return new ResponseEntity<>(adressesResource, HttpStatus.OK);

        }else{
            return new ResponseEntity(new ErrorResponseResource(HttpStatus.NOT_FOUND,"No persone found with id "
                    + personneId),HttpStatus.NOT_FOUND);
        }



    }

    private HttpHeaders putLocationHeader(ResourceObject personneResource) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new UriTemplate(PERSONNE).expand(personneResource.getTechnicalId()));
        return headers;
    }

    private void putSelfLink(ResourceObject personneResource) throws AdresseIncoherenceException {

        personneResource.add(linkTo(methodOn(
                PersonnesController.class).getPersonneById(personneResource.getTechnicalId()))
                .withSelfRel());

        personneResource.add(linkTo(methodOn(
                PersonnesController.class).listerAdresseForPersonne(personneResource.getTechnicalId()))
                .withRel(LIST_ADRESSES));

        personneResource.add(linkTo(methodOn(
                PersonnesController.class).creerAdresseForPersonne(null,personneResource.getTechnicalId()))
                .withRel(ADD_ADRESSE));


    }
}
