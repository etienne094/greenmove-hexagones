package fr.greenmove.facturation.infrastructure.rest;

import fr.greenmove.facturation.application.ports.ConsulterFacture;
import fr.greenmove.facturation.application.ports.RegulariserFacture;
import fr.greenmove.facturation.domaine.FactureDejaPayee;
import fr.greenmove.facturation.domaine.FactureIntrouvable;
import fr.greenmove.facturation.domaine.IdFacture;
import fr.greenmove.facturation.domaine.IdLocation;
import fr.greenmove.facturation.domaine.PaiementRefuse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/factures")
public class FactureController {

    private final ConsulterFacture consulterFacture;
    private final RegulariserFacture regulariserFacture;

    public FactureController(ConsulterFacture consulterFacture, RegulariserFacture regulariserFacture) {
        this.consulterFacture = consulterFacture;
        this.regulariserFacture = regulariserFacture;
    }

    @GetMapping("/{id}")
    public FactureReponse consulter(@PathVariable String id) {
        return FactureReponse.depuis(consulterFacture.consulter(new IdFacture(id)));
    }

    @GetMapping(params = "location")
    public ResponseEntity<FactureReponse> consulterParLocation(@RequestParam String location) {
        return consulterFacture.consulterParLocation(new IdLocation(location))
                .map(facture -> ResponseEntity.ok(FactureReponse.depuis(facture)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/regularisation")
    public FactureReponse regulariser(@PathVariable String id) {
        return FactureReponse.depuis(regulariserFacture.regulariser(new IdFacture(id)));
    }

    @ExceptionHandler(FactureIntrouvable.class)
    ResponseEntity<Map<String, String>> introuvable(FactureIntrouvable e) {
        return erreur(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(FactureDejaPayee.class)
    ResponseEntity<Map<String, String>> dejaPayee(FactureDejaPayee e) {
        return erreur(HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler(PaiementRefuse.class)
    ResponseEntity<Map<String, String>> paiementRefuse(PaiementRefuse e) {
        return erreur(HttpStatus.BAD_GATEWAY, e);
    }

    private static ResponseEntity<Map<String, String>> erreur(HttpStatus statut, RuntimeException e) {
        return ResponseEntity.status(statut).body(Map.of("erreur", e.getMessage()));
    }
}
