package ch.globaz.tmmas.authentificationservice.application.api.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class HelloController {

    @RequestMapping(method = RequestMethod.POST)
    public String authentification() {

        return "Welcome to the home page!";
    }

}
