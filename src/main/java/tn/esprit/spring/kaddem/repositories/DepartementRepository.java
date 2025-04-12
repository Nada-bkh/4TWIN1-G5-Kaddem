package tn.esprit.spring.kaddem.repositories;

import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.kaddem.entities.Departement;

@Repository
public interface DepartementRepository extends JpaRepository<Departement,Integer> {

}
