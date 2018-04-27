package ch.globaz.tmmas.personnesservice.domain;

import ch.globaz.tmmas.personnesservice.domain.model.Localite;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocaliteTest {

    @Test(expected = NullPointerException.class)
    public void assertThatLocaliteCreationWithNullNomThrowException(){

        Localite localite = new Localite(null,8001);

    }

    @Test(expected = NullPointerException.class)
    public void assertThatLocaliteCreationWithNullNpaThrowException(){

        Localite localite = new Localite("LocaliteTes",null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void assertThatLocaliteCreationWithEmptyNameThrowException(){

        Localite localite = new Localite("",1212);

    }


}