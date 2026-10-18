package fr.greenmove.facturation.domaine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrilleTarifaireTest {

    private final GrilleTarifaire grille = GrilleTarifaire.standard();

    @Test
    void vingt_quatre_minutes_de_velo_sans_abonnement_coutent_460_centimes() {
        Montant prix = grille.calculPrix(24, TypeVehicule.VELO, false);

        assertEquals(new Montant(460), prix);
    }

    @Test
    void dix_huit_minutes_de_trottinette_avec_abonnement_coutent_397_centimes() {
        Montant prix = grille.calculPrix(18, TypeVehicule.TROTTINETTE, true);

        assertEquals(new Montant(397), prix);
    }

    @Test
    void cinq_heures_de_velo_sans_abonnement_sont_plafonnees_a_1500_centimes() {
        Montant prix = grille.calculPrix(5 * 60, TypeVehicule.VELO, false);

        assertEquals(new Montant(1500), prix);
    }

    @Test
    void la_reduction_abonne_s_applique_apres_le_plafond() {
        Montant prix = grille.calculPrix(5 * 60, TypeVehicule.VELO, true);

        assertEquals(new Montant(1200), prix);
    }

    @Test
    void zero_minute_coute_la_prise_en_charge() {
        Montant prix = grille.calculPrix(0, TypeVehicule.VELO, false);

        assertEquals(new Montant(100), prix);
    }

    @Test
    void zero_minute_avec_abonnement_coute_la_prise_en_charge_reduite() {
        Montant prix = grille.calculPrix(0, TypeVehicule.TROTTINETTE, true);

        assertEquals(new Montant(80), prix);
    }
}
