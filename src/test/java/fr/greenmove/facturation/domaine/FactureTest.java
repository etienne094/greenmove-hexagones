package fr.greenmove.facturation.domaine;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FactureTest {

    private static final IdFacture ID = new IdFacture("f1");
    private static final IdUsager USAGER = new IdUsager("u1");
    private static final IdLocation LOCATION = new IdLocation("l1");
    private static final Montant MONTANT = new Montant(460);

    @Test
    void une_facture_payee_a_sa_reference_de_paiement() {
        Facture facture = Facture.payee(ID, USAGER, LOCATION, MONTANT, "SIMU-1234");

        assertEquals(StatutFacture.PAYEE, facture.statut());
        assertEquals(Optional.of("SIMU-1234"), facture.referenceDePaiement());
    }

    @Test
    void une_facture_impayee_n_a_pas_de_reference() {
        Facture facture = Facture.impayee(ID, USAGER, LOCATION, MONTANT);

        assertEquals(StatutFacture.IMPAYEE, facture.statut());
        assertTrue(facture.referenceDePaiement().isEmpty());
    }

    @Test
    void une_facture_payee_sans_reference_est_refusee() {
        assertThrows(IllegalArgumentException.class, () -> Facture.payee(ID, USAGER, LOCATION, MONTANT, " "));
    }

    @Test
    void regulariser_une_facture_impayee_la_rend_payee_avec_la_reference() {
        Facture facture = Facture.impayee(ID, USAGER, LOCATION, MONTANT);

        facture.regulariser("SIMU-5678");

        assertEquals(StatutFacture.PAYEE, facture.statut());
        assertEquals(Optional.of("SIMU-5678"), facture.referenceDePaiement());
    }

    @Test
    void regulariser_une_facture_deja_payee_est_refuse_et_ne_change_rien() {
        Facture facture = Facture.payee(ID, USAGER, LOCATION, MONTANT, "SIMU-1234");

        assertThrows(FactureDejaPayee.class, () -> facture.regulariser("SIMU-5678"));
        assertEquals(Optional.of("SIMU-1234"), facture.referenceDePaiement());
    }
}
