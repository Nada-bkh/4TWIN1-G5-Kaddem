package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UniversiteServiceImplTest {

    @Mock
    UniversiteRepository universiteRepository;

    @Mock
    DepartementRepository departementRepository;

    @InjectMocks
    UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUniversite() {
        Universite u = new Universite("ESPRIT");
        when(universiteRepository.save(u)).thenReturn(u);

        Universite result = universiteService.addUniversite(u);
        assertEquals("ESPRIT", result.getNomUniv());
    }

    @Test
    void testRetrieveUniversite() {
        Universite u = new Universite(1, "ISI");
        when(universiteRepository.findById(1)).thenReturn(Optional.of(u));

        Universite result = universiteService.retrieveUniversite(1);
        assertNotNull(result);
        assertEquals("ISI", result.getNomUniv());
    }

    @Test
    void testUpdateUniversite() {
        Universite u = new Universite(2, "ENIS");
        when(universiteRepository.save(u)).thenReturn(u);

        Universite updated = universiteService.updateUniversite(u);
        assertEquals("ENIS", updated.getNomUniv());
    }

    @Test
    void testRetrieveAllUniversites() {
        List<Universite> universites = Arrays.asList(
                new Universite(1, "ESPRIT"),
                new Universite(2, "ENIS")
        );
        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> result = universiteService.retrieveAllUniversites();
        assertEquals(2, result.size());
    }

    @Test
    void testDeleteUniversite() {
        Universite u = new Universite(3, "ENIT");
        when(universiteRepository.findById(3)).thenReturn(Optional.of(u));
        doNothing().when(universiteRepository).delete(u);

        assertDoesNotThrow(() -> universiteService.deleteUniversite(3));
        verify(universiteRepository, times(1)).delete(u);
    }

    @Test
    void testAssignUniversiteToDepartement() {
        Universite u = new Universite(1, "FST");
        Departement d = new Departement(5, "Info");

        Set<Departement> departements = new HashSet<>();
        u.setDepartements(departements);

        when(universiteRepository.findById(1)).thenReturn(Optional.of(u));
        when(departementRepository.findById(5)).thenReturn(Optional.of(d));
        when(universiteRepository.save(u)).thenReturn(u);

        universiteService.assignUniversiteToDepartement(1, 5);

        assertTrue(u.getDepartements().contains(d));
    }

    @Test
    void testRetrieveDepartementsByUniversite() {
        Departement d1 = new Departement(1, "Math");
        Departement d2 = new Departement(2, "Physique");
        Set<Departement> departements = new HashSet<>(Arrays.asList(d1, d2));

        Universite u = new Universite(1, "FSB");
        u.setDepartements(departements);

        when(universiteRepository.findById(1)).thenReturn(Optional.of(u));

        Set<Departement> result = universiteService.retrieveDepartementsByUniversite(1);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
