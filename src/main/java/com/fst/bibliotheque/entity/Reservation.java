package com.fst.bibliotheque.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "livre_id")
    private Livre livre;

    @ManyToOne(optional = false)
    @JoinColumn(name = "membre_id")
    private Membre membre;

    private LocalDate dateReservation;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut = StatutReservation.EN_ATTENTE;

    @PrePersist
    public void prePersist() {
        if (dateReservation == null) {
            dateReservation = LocalDate.now();
        }
    }
}
