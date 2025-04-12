package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DepartementServiceImpl implements IDepartementService {

	@Autowired
	private DepartementRepository departementRepository;

	@Override
	public List<Departement> retrieveAllDepartements() {
		log.info("Retrieving all departements...");
		List<Departement> departements = departementRepository.findAll();
		log.debug("Departements retrieved: {}", departements);
		return departements;
	}

	@Override
	public Departement addDepartement(Departement departement) {
		log.info("Adding new departement: {}", departement);
		Departement savedDepartement = departementRepository.save(departement);
		log.info("Departement added with ID: {}", savedDepartement.getIdDepart());
		return savedDepartement;
	}

	@Override
	public Departement updateDepartement(Departement departement) {
		log.info("Updating departement with ID: {}", departement.getIdDepart());
		Departement updatedDepartement = departementRepository.save(departement);
		log.info("Departement updated: {}", updatedDepartement);
		return updatedDepartement;
	}

	@Override
	public Departement retrieveDepartement(Integer idDepartement) {
		log.info("Retrieving departement with ID: {}", idDepartement);
		Optional<Departement> departement = departementRepository.findById(idDepartement);
		if (departement.isPresent()) {
			log.info("Departement found: {}", departement.get());
			return departement.get();
		} else {
			log.error("Departement with ID {} not found", idDepartement);
			return null;
		}
	}

	@Override
	public void deleteDepartement(Integer idDepartement) {
		log.info("Deleting departement with ID: {}", idDepartement);
		Departement departement = retrieveDepartement(idDepartement);
		if (departement != null) {
			departementRepository.delete(departement);
			log.info("Departement deleted successfully");
		} else {
			log.error("Departement with ID {} does not exist", idDepartement);
		}
	}
}
