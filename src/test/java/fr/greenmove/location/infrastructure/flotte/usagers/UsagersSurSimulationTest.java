package fr.greenmove.location.infrastructure.flotte.usagers;

import fr.greenmove.exterieur.UsagersEnMemoire;
import fr.greenmove.location.domaine.models.IdUsager;
import fr.greenmove.location.domaine.ports.Usagers;
import fr.greenmove.location.infrastructure.usagers.UsagersSurSimulation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsagersSurSimulationTest {

    private final Usagers usagers = new UsagersSurSimulation(new UsagersEnMemoire());

    @Test
    void un_usager_du_csv_existe() {
        assertTrue(usagers.existe(new IdUsager("u1")));
    }

    @Test
    void un_usager_inconnu_n_existe_pas(){
        assertFalse(usagers.existe(new IdUsager("inconnu")));
    }

}


