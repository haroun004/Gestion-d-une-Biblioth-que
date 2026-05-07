package com.fst.bibliotheque.dto;

import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.entity.Membre;

/**
 * Utility class for mapping between entities and DTOs.
 * Manual mapping keeps the build simple (no annotation processor needed).
 */
public final class DtoMapper {

    private DtoMapper() {}

    // ── Livre ────────────────────────────────────────────────────

    public static LivreDTO toDTO(Livre livre) {
        if (livre == null) return null;
        return new LivreDTO(
                livre.getId(),
                livre.getTitre(),
                livre.getAuteur(),
                livre.getIsbn(),
                livre.getCategorie(),
                livre.getQuantiteTotal(),
                livre.getQuantiteDisponible()
        );
    }

    public static Livre toEntity(LivreDTO dto) {
        if (dto == null) return null;
        Livre livre = new Livre();
        livre.setId(dto.getId());
        livre.setTitre(dto.getTitre());
        livre.setAuteur(dto.getAuteur());
        livre.setIsbn(dto.getIsbn());
        livre.setCategorie(dto.getCategorie());
        livre.setQuantiteTotal(dto.getQuantiteTotal());
        livre.setQuantiteDisponible(dto.getQuantiteDisponible());
        return livre;
    }

    // ── Membre ───────────────────────────────────────────────────

    public static MembreDTO toDTO(Membre membre) {
        if (membre == null) return null;
        return new MembreDTO(
                membre.getId(),
                membre.getNom(),
                membre.getPrenom(),
                membre.getEmail(),
                membre.getDateInscription(),
                membre.isActif()
        );
    }

    public static Membre toEntity(MembreDTO dto) {
        if (dto == null) return null;
        Membre membre = new Membre();
        membre.setId(dto.getId());
        membre.setNom(dto.getNom());
        membre.setPrenom(dto.getPrenom());
        membre.setEmail(dto.getEmail());
        membre.setDateInscription(dto.getDateInscription());
        membre.setActif(dto.isActif());
        return membre;
    }

    // ── Emprunt ──────────────────────────────────────────────────

    public static EmpruntViewDTO toViewDTO(Emprunt emprunt) {
        if (emprunt == null) return null;
        EmpruntViewDTO dto = new EmpruntViewDTO();
        dto.setId(emprunt.getId());
        dto.setLivreId(emprunt.getLivre().getId());
        dto.setLivretitre(emprunt.getLivre().getTitre());
        dto.setMembreId(emprunt.getMembre().getId());
        dto.setMembreNomComplet(emprunt.getMembre().getPrenom() + " " + emprunt.getMembre().getNom());
        dto.setMembreEmail(emprunt.getMembre().getEmail());
        dto.setDateEmprunt(emprunt.getDateEmprunt());
        dto.setDateRetourPrevue(emprunt.getDateRetourPrevue());
        dto.setDateRetourEffective(emprunt.getDateRetourEffective());
        dto.setStatut(emprunt.getStatut());
        return dto;
    }
}
