package fr.greenmove.facturation.domaine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MontantTest {

    @Test
    void un_montant_negatif_est_refuse() {
        assertThrows(IllegalArgumentException.class, () -> new Montant(-1));
    }

    @Test
    void un_montant_nul_est_accepte() {
        assertDoesNotThrow(() -> new Montant(0));
    }

    @Test
    void deux_montants_de_meme_valeur_sont_egaux() {
        assertEquals(new Montant(460), new Montant(460));
    }

    @Test
    void appliquer_une_reduction_renvoie_un_nouveau_montant_sans_modifier_l_original() {
        Montant original = new Montant(1500);

        Montant reduit = original.appliquerReduction(20);

        assertEquals(new Montant(1200), reduit);
        assertEquals(new Montant(1500), original);
    }
}
