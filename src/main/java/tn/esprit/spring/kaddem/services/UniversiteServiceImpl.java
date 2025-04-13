package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class UniversiteServiceImpl implements IUniversiteService {

    @Autowired
    UniversiteRepository universiteRepository;

    @Autowired
    DepartementRepository departementRepository;

    private static final Logger logger = LoggerFactory.getLogger(UniversiteServiceImpl.class);

    public UniversiteServiceImpl() {}

    public List<Universite> retrieveAllUniversites() {
        logger.info("Récupération de toutes les universités");
        List<Universite> universites = (List<Universite>) universiteRepository.findAll();
        logger.debug("Nombre d'universités récupérées : {}", universites.size());
        return universites;
    }

    public Universite addUniversite(Universite u) {
        logger.info("Ajout d'une nouvelle université : {}", u.getNomUniv());
        Universite savedUniv = universiteRepository.save(u);
        logger.debug("Université ajoutée avec ID : {}", savedUniv.getIdUniv());
        return savedUniv;
    }

    public Universite updateUniversite(Universite u) {
        logger.info("Mise à jour de l'université avec ID : {}", u.getIdUniv());
        Universite updatedUniv = universiteRepository.save(u);
        logger.debug("Université mise à jour : {}", updatedUniv.getNomUniv());
        return updatedUniv;
    }

    public Universite retrieveUniversite(Integer idUniversite) {
        logger.info("Récupération de l'université avec ID : {}", idUniversite);
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        if (u == null) {
            logger.warn("Aucune université trouvée avec l'ID : {}", idUniversite);
        }
        return u;
    }

    public void deleteUniversite(Integer idUniversite) {
        logger.info("Suppression de l'université avec ID : {}", idUniversite);
        Universite u = retrieveUniversite(idUniversite);
        if (u != null) {
            universiteRepository.delete(u);
            logger.debug("Université supprimée : {}", idUniversite);
        } else {
            logger.error("Impossible de supprimer : université introuvable avec ID : {}", idUniversite);
        }
    }

    public void assignUniversiteToDepartement(Integer idUniversite, Integer idDepartement) {
        logger.info("Affectation du département {} à l'université {}", idDepartement, idUniversite);
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        Departement d = departementRepository.findById(idDepartement).orElse(null);

        if (u != null && d != null) {
            u.getDepartements().add(d);
            universiteRepository.save(u);
            logger.debug("Département {} affecté à l'université {}", idDepartement, idUniversite);
        } else {
            logger.warn("Échec d'affectation - université ou département introuvable (univId={}, depId={})", idUniversite, idDepartement);
        }
    }

    public Set<Departement> retrieveDepartementsByUniversite(Integer idUniversite) {
        logger.info("Récupération des départements de l'université ID : {}", idUniversite);
        Universite u = universiteRepository.findById(idUniversite).orElse(null);
        if (u == null) {
            logger.warn("Université non trouvée pour l'ID : {}", idUniversite);
            return null;
        }
        Set<Departement> deps = u.getDepartements();
        logger.debug("Nombre de départements trouvés : {}", deps.size());
        return deps;
    }
}
