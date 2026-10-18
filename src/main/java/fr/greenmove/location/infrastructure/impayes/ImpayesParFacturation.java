package fr.greenmove.location.infrastructure.impayes;

import fr.greenmove.facturation.application.ports.ConsulterImpayes;
import fr.greenmove.location.domaine.models.IdUsager;
import fr.greenmove.location.domaine.ports.Impayes;

public class ImpayesParFacturation implements Impayes {


    private final ConsulterImpayes facturation;

    public ImpayesParFacturation(ConsulterImpayes facturation){
        this.facturation = facturation;
    }

    @Override
    public boolean aUneFactureImpayee(IdUsager id){
        return facturation.aUneFactureImpayee(id.valeur());
    }

}
