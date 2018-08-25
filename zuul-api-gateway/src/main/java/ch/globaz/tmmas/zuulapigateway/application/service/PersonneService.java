package ch.globaz.tmmas.zuulapigateway.application.service;

import ch.globaz.tmmas.zuulapigateway.application.dto.PersonnesPhysiqueDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("personnes-service")
public interface PersonneService {

    @RequestMapping(method = RequestMethod.GET, value = "/personnes-service/personnes/{personnesId}")
    ResponseEntity<PersonnesPhysiqueDto> getPersonnePhysiqueById(@PathVariable("personnesId") Long tiersId);

    @RequestMapping(method = RequestMethod.GET, value = "/personnes-service/personnes")
    ResponseEntity<List<PersonnesPhysiqueDto>> getPersonnesPhysique();
}
