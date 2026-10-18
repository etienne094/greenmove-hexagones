package fr.greenmove.facturation.domaine.ports;

import fr.greenmove.facturation.domaine.IdUsager;

public interface Abonnements {
    boolean estAbonne(IdUsager idUsager);
}
