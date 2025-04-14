package tn.esprit.spring.kaddem.controllers;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.services.IContratService;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/contrat")
public class ContratRestController {
	IContratService contratService;

	@GetMapping("/retrieve-all-contrats")
	public List<Contrat> getContrats() {
		return contratService.retrieveAllContrats();
	}

	@GetMapping("/retrieve-contrat/{contrat-id}")
	public Contrat retrieveContrat(@PathVariable("contrat-id") Integer contratId) {
		Contrat contrat = contratService.retrieveContrat(contratId);
		if (contrat == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract with ID " + contratId + " not found");
		}
		return contrat;
	}

	@PostMapping("/add-contrat")
	public Contrat addContrat(@RequestBody Contrat c) {
		return contratService.addContrat(c);
	}

	@DeleteMapping("/remove-contrat/{contrat-id}")
	public void removeContrat(@PathVariable("contrat-id") Integer contratId) {
		Contrat contrat = contratService.retrieveContrat(contratId);
		if (contrat == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract with ID " + contratId + " not found");
		}
		contratService.removeContrat(contratId);
	}

	@PutMapping("/update-contrat")
	public Contrat updateContrat(@RequestBody Contrat c) {
		return contratService.updateContrat(c);
	}

	@PutMapping(value = "/assignContratToEtudiant/{idContrat}/{nomE}/{prenomE}")
	public Contrat assignContratToEtudiant(@PathVariable("idContrat") Integer idContrat,
										   @PathVariable("nomE") String nomE,
										   @PathVariable("prenomE") String prenomE) {
		try {
			Contrat contrat = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);
			if (contrat == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contract or student not found");
			}
			return contrat;
		} catch (IllegalStateException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping(value = "/getnbContratsValides/{startDate}/{endDate}")
	public Integer getnbContratsValides(@PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
										@PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
		return contratService.nbContratsValides(startDate, endDate);
	}

	@Scheduled(cron = "0 0 13 * * *")
	@PutMapping(value = "/majStatusContrat")
	public void majStatusContrat() {
		contratService.retrieveAllContrats().forEach(contrat -> {
			if (contrat.getDateFinContrat().before(new Date()) && !contrat.getArchive()) {
				contrat.setArchive(true);
				contratService.updateContrat(contrat);
			}
		});
	}

	@GetMapping("/calculChiffreAffaireEntreDeuxDate/{startDate}/{endDate}")
	@ResponseBody
	public float calculChiffreAffaireEntreDeuxDates(@PathVariable(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
													@PathVariable(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
		return contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);
	}
}