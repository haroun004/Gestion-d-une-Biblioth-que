package com.fst.bibliotheque.dto;

import java.time.LocalDate;

import com.fst.bibliotheque.entity.StatutEmprunt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Read-only DTO for displaying Emprunt data in views.
 * Flattens nested entity fields for clean template access.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpruntViewDTO {

    private Long id;

    private Long livreId;
    private String livretitre;

    private Long membreId;
    private String membreNomComplet;
    private String membreEmail;

    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;

    private StatutEmprunt statut;

    public boolean isRendu() {
        return StatutEmprunt.RENDU == statut;
    }

    public boolean isEnRetard() {
        return StatutEmprunt.EN_RETARD == statut;
    }
}
