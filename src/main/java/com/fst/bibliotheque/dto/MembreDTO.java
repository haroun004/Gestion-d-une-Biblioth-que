package com.fst.bibliotheque.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used for Membre create/edit forms.
 * Decouples form binding from the JPA entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembreDTO {

    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    private LocalDate dateInscription;

    private boolean actif = true;
}
