package com.fst.bibliotheque.dto;

import com.fst.bibliotheque.entity.StatutReservation;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private Long id;
    private Long livreId;
    private String livreTitre;
    private String livreAuteur;
    private Long membreId;
    private String membreNomComplet;
    private String membreEmail;
    private LocalDate dateReservation;
    private StatutReservation statut;
}
