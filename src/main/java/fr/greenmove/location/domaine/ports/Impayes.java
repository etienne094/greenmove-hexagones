package fr.greenmove.location.domaine.ports;

import fr.greenmove.location.domaine.models.IdUsager;

public interface Impayes {
    boolean aUneFactureImpayee (IdUsager id);
}
