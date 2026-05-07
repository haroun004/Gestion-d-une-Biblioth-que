package com.fst.bibliotheque.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used for the Emprunt creation form.
 * Uses IDs for livre and membre instead of entity references,
 * avoiding the need for custom Spring converters.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpruntFormDTO {

    @NotNull(message = "Veuillez sélectionner un livre")
    private Long livreId;

    @NotNull(message = "Veuillez sélectionner un membre")
    private Long membreId;

    @NotNull(message = "La date de retour prévue est obligatoire")
    @Future(message = "La date de retour doit être dans le futur")
    private LocalDate dateRetourPrevue;
}
