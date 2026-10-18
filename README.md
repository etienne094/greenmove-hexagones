# GreenMove – Location et Facturation (TP1 Hexagones) – CAMUS Etienne, SALMI Zakaria

Java 17+ et Maven. Aucune base à installer : H2 tourne en mémoire.

- **Compiler** : `mvn compile`
- **Lancer les tests** (domaines, cas d'usage, adaptateurs, ArchUnit, scénario) : `mvn test`
- **Démarrer** : `mvn spring-boot:run`, puis l'API répond sur `http://localhost:8080`
  (ou depuis IntelliJ : ouvrir `pom.xml` comme projet Maven et lancer `GreenMoveApplication`)
- **Jouer le scénario de l'annexe C** :
   - automatiquement : `mvn test -Dtest=ScenarioBoutEnBoutTest`
   - à la main : les commandes `curl` de l'annexe C de l'énoncé, application démarrée