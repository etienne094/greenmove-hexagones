package fr.greenmove.location.domaine;

import java.time.Instant;

public class FinAnterieureAuDebut extends RuntimeException{
    public FinAnterieureAuDebut(Instant debut, Instant fin){
        super ("Fin " + fin + " antérieure au début " + debut);
    }
}
