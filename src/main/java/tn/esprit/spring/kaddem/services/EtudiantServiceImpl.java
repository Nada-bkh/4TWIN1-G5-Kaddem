package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class EtudiantServiceImpl implements IEtudiantService {
	@Autowired
	EtudiantRepository etudiantRepository;
	@Autowired
	ContratRepository contratRepository;
	@Autowired
	EquipeRepository equipeRepository;
	@Autowired
	DepartementRepository departementRepository;

	public List<Etudiant> retrieveAllEtudiants() {
		log.debug("Fetching all students from database");
		return (List<Etudiant>) etudiantRepository.findAll();
	}

	public Etudiant addEtudiant(Etudiant e) {
		log.info("Saving new student: {} {}", e.getNomE(), e.getPrenomE());
		return etudiantRepository.save(e);
	}

	public Etudiant updateEtudiant(Etudiant e) {
		log.info("Updating student with ID: {}", e.getIdEtudiant());
		return etudiantRepository.save(e);
	}

	public Etudiant retrieveEtudiant(Integer idEtudiant) {
		log.debug("Fetching student with ID: {}", idEtudiant);
		return etudiantRepository.findById(idEtudiant).orElse(null);
	}

	public void removeEtudiant(Integer idEtudiant) {
		log.warn("Deleting student with ID: {}", idEtudiant);
		Etudiant e = retrieveEtudiant(idEtudiant);
		if (e != null) {
			etudiantRepository.delete(e);
			log.info("Student with ID: {} deleted successfully", idEtudiant);
		} else {
			log.error("Student with ID: {} not found", idEtudiant);
		}
	}

	public void assignEtudiantToDepartement(Integer etudiantId, Integer departementId) {
		log.info("Assigning student {} to department {}", etudiantId, departementId);
		Etudiant etudiant = etudiantRepository.findById(etudiantId).orElse(null);
		Departement departement = departementRepository.findById(departementId).orElse(null);
		if (etudiant != null && departement != null) {
			etudiant.setDepartement(departement);
			etudiantRepository.save(etudiant);
			log.info("Student {} assigned to department {}", etudiantId, departementId);
		} else {
			log.error("Failed to assign student {} to department {}: student or department not found", etudiantId, departementId);
		}
	}

	@Transactional
	public Etudiant addAndAssignEtudiantToEquipeAndContract(Etudiant e, Integer idContrat, Integer idEquipe) {
		log.info("Adding and assigning student {} {} to contract {} and team {}", e.getNomE(), e.getPrenomE(), idContrat, idEquipe);
		Contrat c = contratRepository.findById(idContrat).orElse(null);
		Equipe eq = equipeRepository.findById(idEquipe).orElse(null);
		if (c != null && eq != null) {
			c.setEtudiant(e);
			eq.getEtudiants().add(e);
			log.info("Successfully assigned student {} to contract {} and team {}", e.getNomE(), idContrat, idEquipe);
		} else {
			log.error("Failed to assign student to contract {} or team {}", idContrat, idEquipe);
		}
		return e;
	}

	public List<Etudiant> getEtudiantsByDepartement(Integer idDepartement) {
		log.info("Fetching students for department ID: {}", idDepartement);
		return etudiantRepository.findEtudiantsByDepartement_IdDepart(idDepartement);
	}
}
