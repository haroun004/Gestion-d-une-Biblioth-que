# Gestion de Bibliothèque

> **Mini-Projet Spring Boot** — FST · Master STR · Année universitaire 2025–2026
>
> Application web de gestion d'une bibliothèque universitaire : catalogue de livres, membres, emprunts et retours — interface Thymeleaf responsive, sécurisée avec Spring Security.

---

## Table des matières

- [Aperçu](#aperçu)
- [Stack technologique](#stack-technologique)
- [Architecture](#architecture)
- [Prérequis](#prérequis)
- [Lancement rapide — local](#lancement-rapide--local)
- [Lancement avec Docker Compose](#lancement-avec-docker-compose)
- [Comptes par défaut](#comptes-par-défaut)
- [Fonctionnalités](#fonctionnalités)
- [Structure du projet](#structure-du-projet)
- [Variables de configuration](#variables-de-configuration)
- [Données de test](#données-de-test)
- [Auteur](#auteur)

---

## Aperçu

| Écran | Description |
|---|---|
| **Dashboard** | Compteurs en temps réel : livres, membres, emprunts en cours, retards |
| **Livres** | CRUD complet avec recherche full-text et pagination |
| **Membres** | CRUD complet avec recherche et pagination |
| **Emprunts** | Création, retour, filtrage par statut (EN_COURS / RENDU / EN_RETARD) |
| **Sécurité** | Authentification par formulaire, rôles ADMIN / USER |

---

## Stack technologique

| Couche | Technologie |
|---|---|
| Backend | Spring Boot 3.4.5 · Spring MVC · Spring Data JPA |
| Frontend | Thymeleaf 3 · Thymeleaf Layout Dialect · Bootstrap 5.3.3 (WebJars) |
| Base de données | MySQL 8.0 · Hibernate (DDL auto : `update`) |
| Sécurité | Spring Security 6 — in-memory users |
| Validation | Jakarta Bean Validation (`@Valid`) |
| Scheduling | `@Scheduled` — mise à jour automatique des retards à 01h00 |
| Build | Maven 3 · Maven Wrapper (`mvnw`) |
| Conteneurisation | Docker · Docker Compose |

---

## Architecture

```
com.fst.bibliotheque
├── config          # SecurityConfig, WebConfig
├── controller      # LivreController, MembreController, EmpruntController,
│                   # DashboardController, AuthController, GlobalExceptionHandler,
│                   # GlobalModelAdvice
├── dto             # LivreDTO, MembreDTO, EmpruntFormDTO, EmpruntViewDTO, DtoMapper
├── entity          # Livre, Membre, Emprunt, StatutEmprunt
├── repository      # LivreRepository, MembreRepository, EmpruntRepository
└── service         # LivreService, MembreService, EmpruntService
```

**Flux de données :**
```
Browser ──► Controller ──► Service ──► Repository ──► MySQL
              ▲  DTO              Entity
              │
           Thymeleaf
```

---

## Prérequis

| Outil | Version minimale |
|---|---|
| Java (JDK) | 17 |
| Maven | 3.8 (ou utiliser `mvnw`) |
| MySQL | 8.0 |
| Docker & Docker Compose | 20+ (optionnel — lancement conteneurisé) |

---

## Lancement rapide — local

### 1. Cloner le dépôt

```bash
git clone https://github.com/haroun004/Gestion-d-une-Biblioth-que.git
cd Gestion-d-une-Bibliot
```

### 2. Créer la base de données MySQL

```sql
CREATE DATABASE IF NOT EXISTS bibliotheque
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

### 3. Configurer la connexion

Éditez `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bibliotheque?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe
```

> **Conseil sécurité** : ne commitez jamais vos mots de passe. Utilisez un fichier `application-local.properties` (déjà dans `.gitignore`) et lancez avec `-Dspring.profiles.active=local`.

### 4. Démarrer l'application

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

L'application est accessible sur **http://localhost:8081**

Les données de test (20 livres + 10 membres) sont chargées automatiquement depuis `src/main/resources/data.sql` au premier démarrage.

---

## Lancement avec Docker Compose

La configuration Docker orchestre un conteneur **MySQL 8** et un conteneur **application Spring Boot**. La base de données doit être saine avant que l'application démarre (healthcheck configuré).

```bash
# Construire et démarrer les deux services
docker compose up --build

# En arrière-plan
docker compose up --build -d

# Voir les logs de l'application
docker compose logs -f app

# Arrêter et supprimer les conteneurs
docker compose down

# Arrêter ET supprimer le volume MySQL (repart de zéro)
docker compose down -v
```

| Service | URL / Port |
|---|---|
| Application | http://localhost:8081 |
| MySQL | localhost:**3307** (hôte) → 3306 (conteneur) |

---

## Comptes par défaut

| Identifiant | Mot de passe | Rôle | Droits |
|---|---|---|---|
| `admin` | `admin123` | `ROLE_ADMIN` | Toutes les opérations (y compris suppression) |
| `user` | `user123` | `ROLE_USER` | Consultation, création, modification — pas de suppression |

---

## Fonctionnalités

### Livres
- Lister avec **recherche** (titre, auteur, catégorie) et **pagination**
- Ajouter / Modifier / Supprimer (ADMIN uniquement)
- Stock géré automatiquement à chaque emprunt / retour

### Membres
- Lister avec recherche (nom, prénom, email) et pagination
- Ajouter / Modifier / Supprimer (ADMIN uniquement)
- Filtre actif/inactif

### Emprunts
- Créer un emprunt (vérifie la disponibilité du livre)
- Enregistrer le retour (met à jour le stock)
- Filtrage par statut : **EN_COURS** · **RENDU** · **EN_RETARD**
- Mise à jour automatique des retards chaque nuit à 01h00 (`@Scheduled`)

### Tableau de bord
- Compteurs en temps réel : total livres, total membres, emprunts en cours, emprunts en retard

### Sécurité
- Authentification par formulaire sur `/login`
- Protection CSRF activée sur tous les formulaires
- Déconnexion sur `/login?logout`

---

## Structure du projet

```
bibliotheque/
├── docker/
│   └── maven-settings.xml          # Paramètres proxy Maven pour Docker
├── src/
│   ├── main/
│   │   ├── java/com/fst/bibliotheque/
│   │   │   ├── BibliothequeApplication.java
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── data.sql
│   │       └── templates/
│   │           ├── layout/main.html   # Mise en page commune (sidebar)
│   │           ├── dashboard.html
│   │           ├── login.html
│   │           ├── error.html
│   │           ├── livres/
│   │           ├── membres/
│   │           └── emprunts/
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

## Variables de configuration

| Propriété | Valeur par défaut | Description |
|---|---|---|
| `server.port` | `8081` | Port d'écoute de l'application |
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/bibliotheque...` | URL JDBC MySQL |
| `spring.datasource.username` | `root` | Utilisateur MySQL |
| `spring.datasource.password` | *(vide)* | Mot de passe MySQL |
| `spring.jpa.hibernate.ddl-auto` | `update` | Stratégie DDL Hibernate |
| `spring.sql.init.mode` | `always` | Charge `data.sql` au démarrage |
| `spring.thymeleaf.cache` | `false` | Rechargement à chaud des templates |

En environnement Docker, les variables d'environnement du conteneur (`SPRING_DATASOURCE_URL`, etc.) surchargent `application.properties`.

---

## Données de test

Le fichier `src/main/resources/data.sql` insère automatiquement :

- **20 livres** répartis en plusieurs catégories (Informatique, Mathématiques, Littérature, Physique…)
- **10 membres** avec des emails uniques

Les insertions utilisent `INSERT IGNORE` : elles sont idempotentes et ne génèrent pas d'erreur si les données existent déjà.

---

## Auteur

Projet réalisé dans le cadre du cours **Spring Boot / Thymeleaf / MySQL** — FST · Master STR · 2025–2026.

