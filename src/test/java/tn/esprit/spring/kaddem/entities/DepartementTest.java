package tn.esprit.spring.kaddem.entities;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DepartementTest {

    @Test
    void testNoArgsConstructor() {
        Departement departement = new Departement();
        assertNotNull(departement);
    }

    @Test
    void testAllArgsConstructor() {
        Set<Etudiant> etudiants = new HashSet<>();
        Departement departement = new Departement(1, "Informatique");
        departement.setEtudiants(etudiants);

        assertEquals(1, departement.getIdDepart());
        assertEquals("Informatique", departement.getNomDepart());
        assertEquals(etudiants, departement.getEtudiants());
    }

    @Test
    void testConstructorWithNomDepartOnly() {
        Departement departement = new Departement("Mécanique");
        assertEquals("Mécanique", departement.getNomDepart());
        assertNull(departement.getIdDepart());
    }

    @Test
    void testSettersAndGetters() {
        Departement departement = new Departement();
        departement.setIdDepart(10);
        departement.setNomDepart("Génie Civil");

        assertEquals(10, departement.getIdDepart());
        assertEquals("Génie Civil", departement.getNomDepart());
    }
}
