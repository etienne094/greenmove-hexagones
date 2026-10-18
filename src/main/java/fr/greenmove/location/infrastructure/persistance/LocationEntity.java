package fr.greenmove.location.infrastructure.persistance;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "location")

class LocationEntity {
    @Id
    String id;
    String usager;
    String vehicule;
    String type;
    Instant debut;
    Instant fin;
    String statut;

    protected LocationEntity() {
    }

}