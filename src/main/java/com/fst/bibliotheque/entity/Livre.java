package com.fst.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "livres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    @NotBlank(message = "L'auteur est obligatoire")
    private String auteur;

    @NotBlank(message = "L'ISBN est obligatoire")
    @Column(unique = true)
    private String isbn;

    @NotBlank(message = "La catégorie est obligatoire")
    private String categorie;

    @Min(value = 0, message = "La quantité totale doit être positive")
    private int quantiteTotal;

    @Min(value = 0, message = "La quantité disponible doit être positive")
    private int quantiteDisponible;
}
