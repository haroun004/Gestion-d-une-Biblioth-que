package com.fst.bibliotheque.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used for Livre create/edit forms.
 * Decouples form binding from the JPA entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LivreDTO {

    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "L'auteur est obligatoire")
    private String auteur;

    @NotBlank(message = "L'ISBN est obligatoire")
    private String isbn;

    @NotBlank(message = "La catégorie est obligatoire")
    private String categorie;

    @Min(value = 0, message = "La quantité totale doit être positive")
    private int quantiteTotal;

    @Min(value = 0, message = "La quantité disponible doit être positive")
    private int quantiteDisponible;
}
