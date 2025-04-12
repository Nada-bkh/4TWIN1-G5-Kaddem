package tn.esprit.spring.kaddem.services;

import tn.esprit.spring.kaddem.entities.Departement;

import java.util.List;

public interface IDepartementService {
    List<Departement> retrieveAllDepartements();

    Departement addDepartement(Departement departement);

    Departement updateDepartement(Departement departement);

    Departement retrieveDepartement(Integer idDepartement);

    void deleteDepartement(Integer idDepartement);
}
