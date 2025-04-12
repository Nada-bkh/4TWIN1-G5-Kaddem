package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.services.IDepartementService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/departement")
public class DepartementRestController {

	private final IDepartementService departementService;

	// GET http://localhost:8089/Kaddem/departement/retrieve-all-departements
	@GetMapping("/retrieve-all-departements")
	public List<Departement> getDepartements() {
		log.info("Received request to retrieve all departements");
		List<Departement> departements = departementService.retrieveAllDepartements();
		log.debug("Departements retrieved: {}", departements);
		return departements;
	}

	// GET http://localhost:8089/Kaddem/departement/retrieve-departement/{departement-id}
	@GetMapping("/retrieve-departement/{departement-id}")
	public Departement retrieveDepartement(@PathVariable("departement-id") Integer departementId) {
		log.info("Received request to retrieve departement with ID: {}", departementId);
		Departement departement = departementService.retrieveDepartement(departementId);
		if (departement != null) {
			log.debug("Departement found: {}", departement);
		} else {
			log.warn("Departement with ID {} not found", departementId);
		}
		return departement;
	}

	// POST http://localhost:8089/Kaddem/departement/add-departement
	@PostMapping("/add-departement")
	public Departement addDepartement(@RequestBody Departement departement) {
		log.info("Received request to add new departement: {}", departement);
		Departement addedDepartement = departementService.addDepartement(departement);
		log.info("Departement added successfully: {}", addedDepartement);
		return addedDepartement;
	}

	// DELETE http://localhost:8089/Kaddem/departement/remove-departement/{departement-id}
	@DeleteMapping("/remove-departement/{departement-id}")
	public void removeDepartement(@PathVariable("departement-id") Integer departementId) {
		log.info("Received request to delete departement with ID: {}", departementId);
		departementService.deleteDepartement(departementId);
		log.info("Departement with ID {} deleted successfully", departementId);
	}

	// PUT http://localhost:8089/Kaddem/departement/update-departement
	@PutMapping("/update-departement")
	public Departement updateDepartement(@RequestBody Departement departement) {
		log.info("Received request to update departement: {}", departement);
		Departement updatedDepartement = departementService.updateDepartement(departement);
		log.info("Departement updated successfully: {}", updatedDepartement);
		return updatedDepartement;
	}
}
