package ch.globaz.tmmas.personnesservice.domain;

import ch.globaz.tmmas.personnesservice.domain.model.NSS;
import org.junit.Test;

import static org.junit.Assert.*;

public class NSSTest {

    @Test(expected = NullPointerException.class)
    public void assertThatCreateInstanceWithNullNSSThrowException () {

        NSS nss = new NSS(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void assertThatCreateInstanceWithMalFormattedNssNSSThrowException () {

        NSS nss = new NSS("2343");

    }
}