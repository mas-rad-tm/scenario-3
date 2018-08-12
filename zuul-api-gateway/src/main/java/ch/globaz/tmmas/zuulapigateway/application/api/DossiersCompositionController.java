package ch.globaz.tmmas.zuulapigateway.application.api;

import ch.globaz.tmmas.zuulapigateway.application.datamanagement.SampleDataDto;
import ch.globaz.tmmas.zuulapigateway.application.dto.PersonnesPhysiqueDto;
import ch.globaz.tmmas.zuulapigateway.application.dto.DossierDto;
import ch.globaz.tmmas.zuulapigateway.application.service.PersonneService;
import ch.globaz.tmmas.zuulapigateway.application.service.DossierService;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/rentes")
public class DossiersCompositionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DossiersCompositionController.class);

    @Autowired
    PersonneService personnePhysiqueService;

    @Autowired
    DossierService dossierService;

    static final Faker faker = new Faker(new Locale("fr"));

    @RequestMapping(value = "/sample",method = RequestMethod.POST)
    public ResponseEntity<String> createRentesSampleData(@RequestBody SampleDataDto dto) {
        LOGGER.debug("createPerson data samples(), {} elements to generate", dto.getNbValeurs());

        //récupération du nombre de valeurs à générer
        Integer nbRentesToGenerate = Integer.valueOf(dto.getNbValeurs());
        //récupérationd personnes physiques
        ResponseEntity<List<PersonnesPhysiqueDto>> personnesPhysiques = personnePhysiqueService.getPersonnesPhysique();

        Integer nbOfPersonnesPhysique = personnesPhysiques.getBody().size();

        //plafin nbRentes a geerer
        Integer nbRentesToGenereateEffective = (nbRentesToGenerate < nbOfPersonnesPhysique) ? nbRentesToGenerate :
                nbOfPersonnesPhysique;

        LOGGER.debug("createPerson data samples(), found {} personnesPhysique, so {} rentes will be generated ",
                nbOfPersonnesPhysique, nbOfPersonnesPhysique);

        //iteration sur les personnes, et generation des rentes
        personnesPhysiques.getBody().subList(0, nbRentesToGenereateEffective).stream().forEach(personne -> {

            DossierDto rente = DossierDto.from(getNumero(),personne.getId(),"11.11.2111");

            dossierService.sauveRente(rente);

        });

        return ResponseEntity.ok().body("OK Created");
    }

    private String getNumero () {
        return String.valueOf(faker.number().numberBetween(10000,99999));
    }

}
