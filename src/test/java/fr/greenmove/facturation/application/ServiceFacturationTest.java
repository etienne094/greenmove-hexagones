package fr.greenmove.facturation.application;

import fr.greenmove.facturation.domaine.Facture;
import fr.greenmove.facturation.domaine.FactureDejaPayee;
import fr.greenmove.facturation.domaine.FactureIntrouvable;
import fr.greenmove.facturation.domaine.GrilleTarifaire;
import fr.greenmove.facturation.domaine.IdFacture;
import fr.greenmove.facturation.domaine.IdLocation;
import fr.greenmove.facturation.domaine.IdUsager;
import fr.greenmove.facturation.domaine.Montant;
import fr.greenmove.facturation.domaine.PaiementRefuse;
import fr.greenmove.facturation.domaine.StatutFacture;
import fr.greenmove.facturation.domaine.TypeVehicule;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServiceFacturationTest {

    private static final IdUsager USAGER = new IdUsager("u1");
    private static final IdUsager ABONNE = new IdUsager("u2");
    private static final IdLocation LOCATION = new IdLocation("l1");

    private final FacturesEnMemoire factures = new FacturesEnMemoire();
    private final PaiementEspion paiement = new PaiementEspion();
    private final ServiceFacturation service = new ServiceFacturation(
            factures, paiement, usager -> usager.equals(ABONNE), GrilleTarifaire.standard());

    @Test
    void emettre_avec_un_paiement_accepte_cree_une_facture_payee() {
        Facture facture = service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        assertEquals(StatutFacture.PAYEE, facture.statut());
        assertEquals(new Montant(460), facture.montant());
        assertEquals(Optional.of("REF-1"), facture.referenceDePaiement());
    }

    @Test
    void emettre_avec_un_paiement_refuse_cree_une_facture_impayee() {
        paiement.refuserLesDebits();

        Facture facture = service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        assertEquals(StatutFacture.IMPAYEE, facture.statut());
        assertTrue(facture.referenceDePaiement().isEmpty());
    }

    @Test
    void emettre_applique_la_reduction_si_l_usager_est_abonne() {
        Facture facture = service.emettre(ABONNE, LOCATION, 18, TypeVehicule.TROTTINETTE);

        assertEquals(new Montant(397), facture.montant());
    }

    @Test
    void emettre_debite_le_montant_calcule() {
        service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        assertEquals(new Montant(460), paiement.dernierMontantDebite);
    }

    @Test
    void emettre_enregistre_la_facture() {
        Facture facture = service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        assertSame(facture, service.consulter(facture.id()));
    }

    @Test
    void emettre_deux_fois_pour_la_meme_location_renvoie_la_meme_facture_sans_debiter_deux_fois() {
        Facture premiere = service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        Facture seconde = service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        assertSame(premiere, seconde);
        assertEquals(1, paiement.nombreDeDebits);
    }

    @Test
    void regulariser_une_facture_impayee_la_rend_payee_et_l_enregistre() {
        paiement.refuserLesDebits();
        Facture impayee = service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);
        paiement.accepterLesDebits();

        Facture regularisee = service.regulariser(impayee.id());

        assertEquals(StatutFacture.PAYEE, regularisee.statut());
        assertTrue(regularisee.referenceDePaiement().isPresent());
        assertEquals(2, factures.nombreEnregistrements);
    }

    @Test
    void regulariser_une_facture_deja_payee_est_refuse_sans_debiter() {
        Facture payee = service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        assertThrows(FactureDejaPayee.class, () -> service.regulariser(payee.id()));
        assertEquals(1, paiement.nombreDeDebits);
    }

    @Test
    void regulariser_avec_un_paiement_encore_refuse_laisse_la_facture_impayee() {
        paiement.refuserLesDebits();
        Facture impayee = service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        assertThrows(PaiementRefuse.class, () -> service.regulariser(impayee.id()));
        assertEquals(StatutFacture.IMPAYEE, service.consulter(impayee.id()).statut());
    }

    @Test
    void regulariser_une_facture_inconnue_est_refuse() {
        assertThrows(FactureIntrouvable.class, () -> service.regulariser(new IdFacture("inconnue")));
    }

    @Test
    void consulter_une_facture_inconnue_est_refuse() {
        assertThrows(FactureIntrouvable.class, () -> service.consulter(new IdFacture("inconnue")));
    }

    @Test
    void consulter_par_location_retrouve_la_facture_de_la_location() {
        Facture facture = service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        assertEquals(Optional.of(facture), service.consulterParLocation(LOCATION));
    }

    @Test
    void un_usager_dont_le_paiement_a_ete_refuse_a_une_facture_impayee() {
        paiement.refuserLesDebits();
        service.emettre(USAGER, LOCATION, 24, TypeVehicule.VELO);

        assertTrue(service.aUneFactureImpayee("u1"));
        assertFalse(service.aUneFactureImpayee("u2"));
    }
}
