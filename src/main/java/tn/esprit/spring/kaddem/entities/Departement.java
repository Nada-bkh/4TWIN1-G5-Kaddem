package tn.esprit.spring.kaddem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Departement implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idDepart;
    private String nomDepart;
    @OneToMany(mappedBy="departement")
    @JsonIgnore
    private Set<Etudiant> etudiants;

    public Departement(String nomDepart) {
        super();
        this.nomDepart = nomDepart;
    }

    public Departement(Integer idDepart, String nomDepart) {
        super();
        this.idDepart = idDepart;
        this.nomDepart = nomDepart;
    }
}
