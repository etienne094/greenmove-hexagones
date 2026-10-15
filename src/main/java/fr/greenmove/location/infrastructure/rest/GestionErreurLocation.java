package fr.greenmove.location.infrastructure.rest;


import fr.greenmove.location.domaine.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = LocationController.class)
public class GestionErreurLocation {

    public record Erreur(String message){}

    @ExceptionHandler({VehiculeInconnu.class, UsagerInconnu.class, LocationInconnue.class})
    public ResponseEntity<Erreur> introuvable(RuntimeException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Erreur(e.getMessage()));
    }

    @ExceptionHandler({VehiculeIndisponible.class, UsagerDejaEnLoc.class,
                        UsagerAvecFactureImpayee.class, LocationDejaTerminee.class})
    public ResponseEntity<Erreur> conflit(RuntimeException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new Erreur(e.getMessage()));
    }

}
