package fr.greenmove.location.infrastructure.flotte.impayes;

import fr.greenmove.location.domaine.models.IdUsager;
import fr.greenmove.location.domaine.ports.Impayes;
import fr.greenmove.location.infrastructure.impayes.ImpayesParFacturation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class ImpayesParFacturationTest {

    private final Impayes impayes = new ImpayesParFacturation(idUsager -> idUsager.equals("u3"));

    @Test
    void transmet_la_reponse_de_facturation_pour_un_usager_AVEC_impaye(){
        assertTrue(impayes.aUneFactureImpayee(new IdUsager("u3")));
    }

    @Test
    void transmet_la_reponse_de_facturation_pour_un_usager_SANS_impaye(){
        assertFalse(impayes.aUneFactureImpayee(new IdUsager("u1")));
    }

}
