package ch.globaz.tmmas.authentificationservice.application.api.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return "Welcome to the home page!";
    }

}
