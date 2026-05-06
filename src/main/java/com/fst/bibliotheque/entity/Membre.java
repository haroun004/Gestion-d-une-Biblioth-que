package com.fst.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "membres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Column(unique = true)
    private String email;

    private LocalDate dateInscription;

    private boolean actif = true;

    @PrePersist
    public void prePersist() {
        if (dateInscription == null) {
            dateInscription = LocalDate.now();
        }
    }
}
