package fr.greenmove.location.application;

import fr.greenmove.location.domaine.evenements.LocationTerminee;
import fr.greenmove.location.domaine.exceptions.*;
import fr.greenmove.location.domaine.models.*;
import fr.greenmove.location.domaine.ports.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServiceLocationTest {

    // Le faux dépôt : une simple Map
    static class LocationsEnMemoire implements Locations {
        Map<IdLocation, Location> contenu = new HashMap<>();

        public IdLocation prochainIdentifiant() {
            return new IdLocation("L-000" + (contenu.size() + 1));
        }

        public void enregistrer(Location location) {
            contenu.put(location.id(), location);
        }

        public Optional<Location> parId(IdLocation id) {
            return Optional.ofNullable(contenu.get(id));
        }

        public boolean aUneLocationEnCours(IdUsager usager) {
            for (Location l : contenu.values()) {
                if (l.usager().equals(usager) && l.estEnCours()) {
                    return true;
                }
            }
            return false;
        }
    }

    Instant midi = Instant.parse("2026-10-20T12:00:00Z");
    IdUsager u1 = new IdUsager("u1");
    IdVehicule v428 = new IdVehicule("V428");

    LocationsEnMemoire locations = new LocationsEnMemoire();
    List<LocationTerminee> publies = new ArrayList<>();

    ServiceLocation service = new ServiceLocation(
            locations,
            id -> Optional.of(new Vehicule(id, TypeVehicule.VELO, true)), // Flotte : tout véhicule existe et est dispo
            id -> true,                                                             // Usagers : tout usager existe
            id -> false,                                                            // Impayes : dit toujours non
            evenement -> publies.add(evenement),                             // Publication : on garde l'événement
            () -> midi);                                                                     // Horloge : toujours midi

    @Test
    void demarrer_cree_une_location_en_cours() {
        Location l = service.demarrer(u1, v428);

        assertEquals(Statut.EN_COURS, l.statut());
        assertEquals(midi, l.debut());
    }

    @Test
    void on_ne_peut_pas_louer_deux_fois_en_meme_temps() {
        service.demarrer(u1, v428);

        assertThrows(UsagerDejaEnLoc.class, () -> service.demarrer(u1, v428));
    }

    @Test
    void terminer_publie_l_evenement() {
        Location l = service.demarrer(u1, v428);

        service.terminer(l.id());

        assertEquals(1, publies.size());
    }
}