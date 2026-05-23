# Hospital App

Application Web JEE de gestion des patients avec Spring Boot 3.2, Spring MVC, Thymeleaf, Spring Data JPA et Spring Security 6.

## Lancer dans IntelliJ IDEA

1. Ouvrir IntelliJ IDEA.
2. File > Open.
3. Selectionner le dossier `hospital-app`.
4. Attendre l'import Maven.
5. Verifier que le SDK du projet est Java 17.
6. Lancer `ma.fsm.hospitalapp.HospitalAppApplication`.
7. Ouvrir `http://localhost:8084`.

## Comptes crees au demarrage

| Username | Password | Roles |
|---|---|---|
| user1 | 1234 | USER |
| admin | 1234 | USER, ADMIN |

## Base de donnees

Par defaut, le projet utilise H2 en memoire.

Console H2 : `http://localhost:8084/h2-console`

Parametres H2 :

- JDBC URL : `jdbc:h2:mem:hospital-db`
- User : `sa`
- Password : vide

Pour utiliser MySQL, lancer avec le profil `mysql` :

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

Configurer ensuite `src/main/resources/application-mysql.properties` selon votre machine.
