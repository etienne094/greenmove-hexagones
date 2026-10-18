package fr.greenmove.location.domaine;


import fr.greenmove.location.domaine.evenements.LocationTerminee;
import fr.greenmove.location.domaine.exceptions.FinAnterieureAuDebut;
import fr.greenmove.location.domaine.exceptions.LocationDejaTerminee;
import fr.greenmove.location.domaine.exceptions.VehiculeIndisponible;
import fr.greenmove.location.domaine.models.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    private static final Instant DEBUT = Instant.parse("2026-10-20T13:40:00Z");
    private static final IdLocation ID = new IdLocation("L-0001");
    private static final IdUsager U1 = new IdUsager("u1");
    private static final Vehicule VELO_DISPO = new Vehicule(new IdVehicule("V428"), TypeVehicule.VELO, true);

    @Test
    void une_location_demarree_est_en_cours() {
        Location l = Location.demarrer(ID, U1, VELO_DISPO, DEBUT);

        assertEquals(Statut.EN_COURS, l.statut());
        assertTrue(l.dureeMinutes().isEmpty());
    }

    @Test
    void la_duree_est_arrondie_a_la_minute_inferieure() {
        Location l = Location.demarrer(ID, U1, VELO_DISPO, DEBUT);

        l.terminer(DEBUT.plusSeconds(24 * 60 + 59)); //24 min

        assertEquals(24, l.dureeMinutes().getAsLong());

    }

    @Test
    void on_ne_termine_pas_deux_fois() {
        Location l = Location.demarrer(ID, U1, VELO_DISPO, DEBUT);
        l.terminer(DEBUT.plusSeconds(60));

        assertThrows(LocationDejaTerminee.class,() -> l.terminer(DEBUT.plusSeconds(120)));
    }

    @Test
    void on_ne_demarre_pas_sur_un_vehicule_indisponible() {
        Vehicule estIndisponible = new Vehicule(new IdVehicule("V502"), TypeVehicule.VELO, false);

        assertThrows(VehiculeIndisponible.class,() -> Location.demarrer(ID, U1, estIndisponible, DEBUT));
    }

    @Test
    void la_fin_ne_peut_pas_preceder_le_debut(){
        Location l = Location.demarrer(ID, U1, VELO_DISPO, DEBUT);

        assertThrows(FinAnterieureAuDebut.class, () -> l.terminer(DEBUT.minusSeconds(1)));
        assertEquals(Statut.EN_COURS, l.statut());
    }

    @Test
    void terminer_renvoie_l_evenement_pour_facturation(){
        Location l = Location.demarrer(ID, U1, VELO_DISPO, DEBUT);
        Instant fin = DEBUT.plusSeconds(600);

        LocationTerminee evt = l.terminer(fin);

        assertEquals(new LocationTerminee("L-0001", "u1", "VELO", DEBUT, fin, 10), evt);
    }

}
