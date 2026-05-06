package com.fst.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "emprunts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emprunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "livre_id")
    private Livre livre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "membre_id")
    private Membre membre;

    private LocalDate dateEmprunt;

    @NotNull(message = "La date de retour prévue est obligatoire")
    private LocalDate dateRetourPrevue;

    private LocalDate dateRetourEffective;

    @Enumerated(EnumType.STRING)
    private StatutEmprunt statut = StatutEmprunt.EN_COURS;

    @PrePersist
    public void prePersist() {
        if (dateEmprunt == null) {
            dateEmprunt = LocalDate.now();
        }
        if (statut == null) {
            statut = StatutEmprunt.EN_COURS;
        }
    }
}
