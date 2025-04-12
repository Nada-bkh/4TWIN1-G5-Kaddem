package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.services.IEquipeService;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/equipe")
public class EquipeRestController {

	IEquipeService equipeService;

	@GetMapping("/retrieve-all-equipes")
	public List<Equipe> getEquipes() {
		log.info("HTTP GET : /retrieve-all-equipes");
		return equipeService.retrieveAllEquipes();
	}

	@GetMapping("/retrieve-equipe/{equipe-id}")
	public Equipe retrieveEquipe(@PathVariable("equipe-id") Integer equipeId) {
		log.info("HTTP GET : /retrieve-equipe/{}", equipeId);
		return equipeService.retrieveEquipe(equipeId);
	}

	@PostMapping("/add-equipe")
	public Equipe addEquipe(@RequestBody Equipe e) {
		log.info("HTTP POST : /add-equipe");
		return equipeService.addEquipe(e);
	}

	@DeleteMapping("/remove-equipe/{equipe-id}")
	public void removeEquipe(@PathVariable("equipe-id") Integer equipeId) {
		log.info("HTTP DELETE : /remove-equipe/{}", equipeId);
		equipeService.deleteEquipe(equipeId);
	}

	@PutMapping("/update-equipe")
	public Equipe updateEtudiant(@RequestBody Equipe e) {
		log.info("HTTP PUT : /update-equipe");
		return equipeService.updateEquipe(e);
	}

	@PutMapping("/faireEvoluerEquipes")
	public void faireEvoluerEquipes() {
		log.info("HTTP PUT : /faireEvoluerEquipes");
		equipeService.evoluerEquipes();
	}
}
