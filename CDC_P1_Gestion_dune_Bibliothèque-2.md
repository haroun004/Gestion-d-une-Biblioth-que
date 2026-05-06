**Mini-Projet Spring Boot — Gestion d'une Bibliothèque** FST — Master STR — 2025/2026![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.001.png)

UNIVERSITÉ DE TUNIS EL MANAR

Faculté des Sciences de Tunis — FST![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.002.png)

Master STR · Mini-Projet Spring Boot / Thymeleaf / MySQL Année Universitaire 2025–2026

**CAHIER DES CHARGES![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.003.png)**

**Gestion d'une Bibliothèque![ref1]**



|**Projet N°**|1 / 4|
| - | - |
|**Durée**|4 semaines|
|**Technologies**|Spring Boot 3 · Thymeleaf 3 · MySQL 8|
|**Encadrant**|Nader Belhadj — Software Ing. | Chercheur IA|

1. **Présentation du projet![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.005.png)**

Ce projet consiste à développer une application web de gestion d'une bibliothèque universitaire. L'application permettra aux administrateurs de gérer le catalogue de livres, les membres (adhérents), ainsi que les opérations d'emprunt et de retour. L'interface doit être claire, responsive et entièrement rendue côté serveur avec Thymeleaf.![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.006.png)

2. **Objectifs pédagogiques**

Maîtriser la conception d'une application Spring Boot avec architecture MVC. Implémenter des relations JPA (OneToMany, ManyToMany) entre entités. Gérer l'état d'un objet métier (disponible, emprunté, en retard).

Mettre en place une recherche et un filtrage dynamique côté serveur. Produire un livrable fonctionnel avec une interface Thymeleaf soignée.![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.007.png)

3. **Stack technologique![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.008.png)**



|**Couche**|**Technologie**|
| - | - |
|**Backend**|Spring Boot 3.x, Spring MVC, Spring Data JPA|
|**Frontend**|Thymeleaf 3, Bootstrap 5, Thymeleaf Layout Dialect|
|**Base de données**|MySQL 8, Hibernate (DDL auto : update)|
|**Sécurité**|Spring Security (authentification admin/user)|
|**Build & outils**|Maven, IntelliJ IDEA / VS Code, Git|

4. **Fonctionnalités requises**
1. **Fonctionnalités obligatoires**

CRUD complet pour les Livres (titre, auteur, ISBN, catégorie, quantité).

CRUD complet pour les Membres (nom, prénom, email, date d'inscription). Gestion des Emprunts : créer un emprunt, enregistrer le retour, calculer les retards. Tableau de bord : nb de livres, membres, emprunts en cours, livres en retard. Recherche de livres par titre, auteur ou catégorie.

Liste paginée des emprunts avec filtre par statut (en cours / rendu / en retard). Validation des formulaires (@Valid, messages d'erreur Thymeleaf). Authentification admin avec Spring Security.

2. **Fonctionnalités optionnelles (bonus)**

Export de la liste des emprunts en cours au format CSV.

Envoi d'un email de rappel simulé pour les retards (JavaMailSender mock). Historique complet des emprunts par membre.

Système de réservation de livres non disponibles.![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.009.png)![ref1]

5. **Modèle de données — Entités JPA principales**

**Entité : Livre**

id (Long, PK)

titre (String, NotBlank)

auteur (String)

isbn (String, Unique)

categorie (String)

quantiteTotal (int)

quantiteDisponible (int)

**Entité : Membre**

id (Long, PK)

nom, prenom, email (Unique)

dateInscription (LocalDate)

actif (boolean)

**Entité : Emprunt**

id (Long, PK)

livre (ManyToOne), membre (ManyToOne)

dateEmprunt, dateRetourPrevue, dateRetourEffective (LocalDate) statut (Enum : EN\_COURS, RENDU, EN\_RETARD)![ref2]

6. **Architecture des packages**

L'application doit respecter l'architecture MVC avec la structure de packages suivante :

com.fst.bibliotheque.entity — Classes JPA (@Entity)

com.fst.bibliotheque.repository — Interfaces Spring Data JPA com.fst.bibliotheque.service — Logique métier (@Service) com.fst.bibliotheque.controller — Contrôleurs MVC (@Controller) com.fst.bibliotheque.dto — Objets de transfert de données com.fst.bibliotheque.config — Configuration Spring Security src/main/resources/templates/ — Pages Thymeleaf (.html) src/main/resources/static/ — CSS, JS, images![ref3]

7. **Planification sur 4 semaines![ref1]**



|**Sem.**|**Objectifs**|**Livrables attendus**|
| - | - | - |
|**S1**|Analyse & Setup|*Diagramme de classes, schéma BDD, entités JPA créées, BDD connectée.*|
|**S2**|Couche données & service|*Repositories, services CRUD Livre/Membre, données de test SQL.*|
|**S3**|Contrôleurs & vues Thymeleaf|*Pages CRUD, recherche, gestion des emprunts, validations formulaires.*|

**S4** Sécurité, dashboard & finition *Spring Security, dashboard, rapport technique PDF,![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.012.png)*

*démo live.![ref2]*

8. **Contraintes techniques**

Code versionné sur Git (GitHub/GitLab, au moins 3 commits par semaine).

Aucun framework JS côté client (React, Angular…) — Thymeleaf uniquement.

La BDD MySQL doit contenir au moins 20 livres et 10 membres de test.

Toutes les exceptions gérées avec des pages d'erreur Thymeleaf personnalisées. Le projet doit démarrer avec mvn spring-boot:run sans configuration supplémentaire. Un fichier README.md doit documenter l'installation et le lancement du projet.![ref3]

9. **Grille d'évaluation![](Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.013.png)**



|**Critère d'évaluation**|**Points**|
| - | - |
|Qualité et complétude du code (architecture MVC, bonnes pratiques)|**5 pts**|
|Fonctionnalités obligatoires implémentées et fonctionnelles|**6 pts**|
|Qualité de l'interface Thymeleaf (design, UX, validations)|**4 pts**|
|Respect du planning et versionning Git|**2 pts**|
|Rapport technique et soutenance orale|**3 pts**|
|**TOTAL**|**20 pts**|

10. **Consignes de remise**
1. Dépôt Git public (GitHub ou GitLab) partagé avec l'encadrant avant la fin de S4.
1. Archive ZIP du projet Maven (sans le dossier target/) sur la plateforme pédagogique.
1. Rapport technique PDF (8–12 pages) : architecture, diagrammes, captures, difficultés.
1. Présentation orale 15 min + 5 min questions en fin de semaine 4.
1. Script SQL d'initialisation (schema.sql + data.sql) inclus dans le projet.

*Bon courage et bon travail !![ref1]*
Encadrant : Nader Belhadj — Software Ing. | Chercheur IA Page 4 Cahier des Charges

[ref1]: Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.004.png
[ref2]: Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.010.png
[ref3]: Aspose.Words.b86e36a3-1227-4ff1-ab85-f3e4a1641ecc.011.png
