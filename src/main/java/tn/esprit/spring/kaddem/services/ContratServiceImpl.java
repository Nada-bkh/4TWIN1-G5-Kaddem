package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ContratServiceImpl implements IContratService {
	@Autowired
	ContratRepository contratRepository;
	@Autowired
	EtudiantRepository etudiantRepository;

	public List<Contrat> retrieveAllContrats() {
		log.info("Retrieving all contracts");
		List<Contrat> contrats = (List<Contrat>) contratRepository.findAll();
		log.debug("Retrieved {} contracts", contrats.size());
		return contrats;
	}

	public Contrat updateContrat(Contrat ce) {
		log.info("Updating contract with ID: {}", ce.getIdContrat());
		Contrat updated = contratRepository.save(ce);
		log.debug("Updated contract: {}", updated);
		return updated;
	}

	public Contrat addContrat(Contrat ce) {
		log.info("Adding new contract: {}", ce);
		Contrat saved = contratRepository.save(ce);
		log.debug("Added contract with ID: {}", saved.getIdContrat());
		return saved;
	}

	public Contrat retrieveContrat(Integer idContrat) {
		log.info("Retrieving contract with ID: {}", idContrat);
		Contrat contrat = contratRepository.findById(idContrat).orElse(null);
		if (contrat == null) {
			log.warn("Contract with ID: {} not found", idContrat);
		} else {
			log.debug("Retrieved contract: {}", contrat);
		}
		return contrat;
	}

	public void removeContrat(Integer idContrat) {
		log.info("Removing contract with ID: {}", idContrat);
		Contrat c = retrieveContrat(idContrat);
		if (c == null) {
			log.error("Cannot remove contract with ID: {} - not found", idContrat);
			return;
		}
		contratRepository.delete(c);
		log.debug("Contract with ID: {} removed", idContrat);
	}

	public Contrat affectContratToEtudiant(Integer idContrat, String nomE, String prenomE) {
		log.info("Affecting contract ID: {} to student: {} {}", idContrat, nomE, prenomE);
		Etudiant e = etudiantRepository.findByNomEAndPrenomE(nomE, prenomE);
		Contrat ce = contratRepository.findByIdContrat(idContrat);
		if (e == null || ce == null) {
			log.error("Student or contract not found for ID: {}, Student: {} {}", idContrat, nomE, prenomE);
			return ce;
		}
		Set<Contrat> contrats = e.getContrats();
		Integer nbContratssActifs = 0;
		if (contrats.size() != 0) {
			for (Contrat contrat : contrats) {
				if (contrat.getArchive() != null && !contrat.getArchive()) {
					nbContratssActifs++;
				}
			}
		}
		log.debug("Active contracts for student: {}", nbContratssActifs);
		if (nbContratssActifs <= 4) {
			ce.setEtudiant(e);
			contratRepository.save(ce);
			log.info("Contract ID: {} successfully assigned to student: {} {}", idContrat, nomE, prenomE);
		} else {
			log.warn("Cannot assign contract - student already has 5 active contracts");
		}
		return ce;
	}

	public Integer nbContratsValides(Date startDate, Date endDate) {
		log.info("Calculating valid contracts between {} and {}", startDate, endDate);
		Integer count = contratRepository.getnbContratsValides(startDate, endDate);
		log.debug("Number of valid contracts: {}", count);
		return count;
	}

	// Replaced method
	public void countActiveContrats() {
		log.info("Counting active contracts");
		List<Contrat> contrats = contratRepository.findAll();
		long activeCount = contrats.stream()
				.filter(contrat -> contrat.getArchive() != null && !contrat.getArchive())
				.count();
		log.info("Number of active contracts: {}", activeCount);
	}

	public float getChiffreAffaireEntreDeuxDates(Date startDate, Date endDate) {
		log.info("Calculating revenue between {} and {}", startDate, endDate);
		float difference_In_Time = endDate.getTime() - startDate.getTime();
		float difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;
		float difference_In_months = difference_In_Days / 30;
		List<Contrat> contrats = contratRepository.findAll();
		float chiffreAffaireEntreDeuxDates = 0;
		for (Contrat contrat : contrats) {
			if (contrat.getSpecialite() == Specialite.IA) {
				chiffreAffaireEntreDeuxDates += (difference_In_months * 300);
			} else if (contrat.getSpecialite() == Specialite.CLOUD) {
				chiffreAffaireEntreDeuxDates += (difference_In_months * 400);
			} else if (contrat.getSpecialite() == Specialite.RESEAUX) {
				chiffreAffaireEntreDeuxDates += (difference_In_months * 350);
			} else {
				chiffreAffaireEntreDeuxDates += (difference_In_months * 450);
			}
		}
		log.debug("Revenue calculated: {}", chiffreAffaireEntreDeuxDates);
		return chiffreAffaireEntreDeuxDates;
	}
}