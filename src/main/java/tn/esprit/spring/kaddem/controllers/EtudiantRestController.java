package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/etudiant")
@Slf4j
public class EtudiantRestController {
	@Autowired
	IEtudiantService etudiantService;

	@GetMapping("/retrieve-all-etudiants")
	public List<Etudiant> getEtudiants() {
		log.info("Getting all students");
		List<Etudiant> listEtudiants = etudiantService.retrieveAllEtudiants();
		log.debug("Retrieved {} students", listEtudiants.size());
		return listEtudiants;
	}

	@GetMapping("/retrieve-etudiant/{etudiant-id}")
	public Etudiant retrieveEtudiant(@PathVariable("etudiant-id") Integer etudiantId) {
		log.info("Retrieving student with ID: {}", etudiantId);
		return etudiantService.retrieveEtudiant(etudiantId);
	}

	@PostMapping("/add-etudiant")
	public Etudiant addEtudiant(@RequestBody Etudiant e) {
		log.info("Adding a new student: {} {}", e.getNomE(), e.getPrenomE());
		Etudiant etudiant = etudiantService.addEtudiant(e);
		log.debug("Added student with ID: {}", etudiant.getIdEtudiant());
		return etudiant;
	}

	@DeleteMapping("/remove-etudiant/{etudiant-id}")
	public void removeEtudiant(@PathVariable("etudiant-id") Integer etudiantId) {
		log.warn("Removing student with ID: {}", etudiantId);
		etudiantService.removeEtudiant(etudiantId);
	}

	@PutMapping("/update-etudiant")
	public Etudiant updateEtudiant(@RequestBody Etudiant e) {
		log.info("Updating student with ID: {}", e.getIdEtudiant());
		return etudiantService.updateEtudiant(e);
	}

	@PutMapping(value="/affecter-etudiant-departement/{etudiantId}/{departementId}")
	public void affecterEtudiantToDepartement(@PathVariable("etudiantId") Integer etudiantId, @PathVariable("departementId") Integer departementId){
		log.info("Assigning student {} to department {}", etudiantId, departementId);
		etudiantService.assignEtudiantToDepartement(etudiantId, departementId);
	}

	@PostMapping("/add-assign-Etudiant/{idContrat}/{idEquipe}")
	@ResponseBody
	public Etudiant addEtudiantWithEquipeAndContract(@RequestBody Etudiant e, @PathVariable("idContrat") Integer idContrat, @PathVariable("idEquipe") Integer idEquipe) {
		log.info("Adding student {} {} with contract {} and team {}", e.getNomE(), e.getPrenomE(), idContrat, idEquipe);
		return etudiantService.addAndAssignEtudiantToEquipeAndContract(e, idContrat, idEquipe);
	}

	@GetMapping(value = "/getEtudiantsByDepartement/{idDepartement}")
	public List<Etudiant> getEtudiantsParDepartement(@PathVariable("idDepartement") Integer idDepartement) {
		log.info("Getting students by department ID: {}", idDepartement);
		return etudiantService.getEtudiantsByDepartement(idDepartement);
	}
}
