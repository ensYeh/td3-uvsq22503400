package fr.uvsq.dns;

import static org.junit.Assert.*;
import org.junit.Test;

public class AdresseIPTest {

    @Test
    public void testAdresseIPValide() {
        AdresseIP ip = new AdresseIP("192.168.0.1");
        assertEquals("192.168.0.1", ip.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAdresseIPInvalide() {
        new AdresseIP("999.999.999.999");  // Doit lancer une exception
    }
}
